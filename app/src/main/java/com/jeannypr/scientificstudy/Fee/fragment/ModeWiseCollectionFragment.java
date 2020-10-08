package com.jeannypr.scientificstudy.Fee.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jeannypr.scientificstudy.Base.Constants;
import com.jeannypr.scientificstudy.Base.Model.UserModel;
import com.jeannypr.scientificstudy.Base.Repo.DataRepo;
import com.jeannypr.scientificstudy.Fee.adapter.HeadWiseCollectionAdapter;
import com.jeannypr.scientificstudy.Fee.adapter.ModeWiseCollectionAdapter;
import com.jeannypr.scientificstudy.Fee.api.FeeService;
import com.jeannypr.scientificstudy.Fee.model.HeadWiseCollectionBean;
import com.jeannypr.scientificstudy.Fee.model.HeadWiseCollectionModel;
import com.jeannypr.scientificstudy.Preference.UserPreference;
import com.jeannypr.scientificstudy.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModeWiseCollectionFragment extends Fragment implements View.OnClickListener {
    View view;
    Context context;
    private UserModel userData;
    private FeeService feeService;
    private ArrayList<HeadWiseCollectionModel> headWiseCollectionModels;
    private ArrayList<HeadWiseCollectionModel> modeWiseCollectionModels;
    private HeadWiseCollectionAdapter headWiseCollectionAdapter;
    private ModeWiseCollectionAdapter modeWiseCollectionAdapter;
    RecyclerView head_collectionList, mode_collectionList;
    private Calendar calendar;
    private SimpleDateFormat df, df2;
    ImageView fromDateIc, toDateIc;
    private String toDate, fromDate, startDate, endDate;
    private TextView txtFromDate, txtTodate, noRecordMsg, headTotalVal, modeTotalVal;
    private int year_fromDate, month_fromDate, day_fromDate;
    long sDateInMillisec, eDateInMillisec;
    LinearLayout noRecord;
    ProgressBar pb;
    long headCollection, modeCollection;
    private FloatingActionButton fabBtn;


    public ModeWiseCollectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return view = inflater.inflate(R.layout.fragment_head_wise_collection, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        userData = UserPreference.getInstance(context).getUserData();
        feeService = new DataRepo<>(FeeService.class, context).getService();


        df = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);
        df2 = new SimpleDateFormat("dd-MMMM-yyyy", Locale.ENGLISH);

        calendar = Calendar.getInstance(TimeZone.getDefault());
        startDate = df2.format(calendar.getTime());
        endDate = df2.format(calendar.getTime());

        headWiseCollectionModels = new ArrayList<>();
        modeWiseCollectionModels = new ArrayList<>();

        txtFromDate = view.findViewById(R.id.fromDateLbl);
        txtFromDate.setOnClickListener(this);

        txtTodate = view.findViewById(R.id.toDateLbl);
        txtTodate.setOnClickListener(this);

      /*  txtFromDate = view.findViewById(R.id.fromDateVal_Collection);
        txtFromDate.setOnClickListener(this);
        fromDateIc = view.findViewById(R.id.fromDateIc_Collection);
        fromDateIc.setOnClickListener(this);

        txtTodate = view.findViewById(R.id.toDateVal_Collection);
        txtTodate.setOnClickListener(this);
        toDateIc = view.findViewById(R.id.toDateIc_Collection);
        toDateIc.setOnClickListener(this);*/

        /*fabBtn = view.findViewById(R.id.fabBtn);
        fabBtn.setOnClickListener(this);*/

        txtFromDate.setText(df.format(calendar.getTime()));
        txtTodate.setText(df.format(calendar.getTime()));

        noRecord = view.findViewById(R.id.noRecord);
        noRecordMsg = view.findViewById(R.id.noRecordMsg);
        pb = view.findViewById(R.id.progressBar);

        modeTotalVal = view.findViewById(R.id.modeTotalVal);
       // headTotalVal = view.findViewById(R.id.headTotalVal);

        head_collectionList = view.findViewById(R.id.head_collectionList);
        mode_collectionList = view.findViewById(R.id.mode_collectionList);

        headWiseCollectionAdapter = new HeadWiseCollectionAdapter(context, headWiseCollectionModels);
        head_collectionList.setAdapter(headWiseCollectionAdapter);
        mode_collectionList.setAdapter(headWiseCollectionAdapter);
        head_collectionList.setLayoutManager(new LinearLayoutManager(context));

        modeWiseCollectionAdapter = new ModeWiseCollectionAdapter(context, modeWiseCollectionModels);
        mode_collectionList.setAdapter(modeWiseCollectionAdapter);
        mode_collectionList.setLayoutManager(new LinearLayoutManager(context));

        ShowHeadCollectionData();
    }

    private void ShowHeadCollectionData() {
        /*headWiseCollectionModels.add(new HeadWiseCollectionModel("Transport", "10000"));
        headWiseCollectionModels.add(new HeadWiseCollectionModel("Fee", "2000"));
        headWiseCollectionModels.add(new HeadWiseCollectionModel("Tution Fee", "1000"));
        headWiseCollectionModels.add(new HeadWiseCollectionModel("Late Fee", "15000"));
        modeWiseCollectionModels.add(new HeadWiseCollectionModel("Cash", "25000"));
        modeWiseCollectionModels.add(new HeadWiseCollectionModel("Credit Card", "2000"));
        modeWiseCollectionModels.add(new HeadWiseCollectionModel("Online payment", "2000"));*/

        pb.setVisibility(View.VISIBLE);

        feeService.GetHeadWiseCollection(startDate, endDate, userData.getSchoolId(), userData.getAcademicyearId()).enqueue(new Callback<HeadWiseCollectionBean>() {
            @Override
            public void onResponse(Call<HeadWiseCollectionBean> call, Response<HeadWiseCollectionBean> response) {
                HeadWiseCollectionBean resp = response.body();

                modeWiseCollectionModels.clear();
                headWiseCollectionModels.clear();

                headCollection = 0;
                modeCollection = 0;
                if (resp != null) {

                    if (resp.Data != null) {

                        if (resp.rcode == Constants.Rcode.OK) {

                            for (HeadWiseCollectionModel collectionModel : resp.Data.HeadWiseCollection) {
                                headWiseCollectionModels.add(collectionModel);
                                headCollection += collectionModel.getAmount();
                            }
                            for (HeadWiseCollectionModel model : resp.Data.ModeWiseCollection) {
                                modeWiseCollectionModels.add(model);
                                modeCollection += model.getAmount();
                            }

                            headTotalVal.setText(String.valueOf(headCollection));
                            modeTotalVal.setText(String.valueOf(modeCollection));

                            headWiseCollectionAdapter.notifyDataSetChanged();
                            modeWiseCollectionAdapter.notifyDataSetChanged();

                        } else if (resp.rcode == Constants.Rcode.NORECORDS) {
                            noRecord.setVisibility(View.VISIBLE);
                            noRecordMsg.setText(R.string.noRecordMsg);
                        } else {
                            Toast.makeText(context, R.string.somethingWrongMsg, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(context, R.string.somethingWrongMsg, Toast.LENGTH_LONG).show();
                    }
                }
                pb.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<HeadWiseCollectionBean> call, Throwable t) {
                Toast.makeText(context, R.string.somethingWrongMsg, Toast.LENGTH_LONG).show();
                pb.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


            case R.id.fromDateLbl:

                DatePickerDialog fromDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        year_fromDate = year;
                        month_fromDate = month;
                        day_fromDate = dayOfMonth;

                        calendar.set(year, month, dayOfMonth);
                        sDateInMillisec = calendar.getTimeInMillis();
                        fromDate = df.format(calendar.getTime());
                        startDate = df2.format(calendar.getTime());
                        txtFromDate.setText(fromDate);
                        ShowHeadCollectionData();

                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                if (eDateInMillisec != 0) {
                    fromDateDialog.getDatePicker().setMaxDate(eDateInMillisec);
                }

                fromDateDialog.show();
                break;


            case R.id.toDateLbl:

                DatePickerDialog toDateDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        calendar.set(year, month, dayOfMonth);
                        eDateInMillisec = calendar.getTimeInMillis();
                        toDate = df.format(calendar.getTime());
                        endDate = df2.format(calendar.getTime());
                        txtTodate.setText(toDate);
                        ShowHeadCollectionData();

                    }
                },
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));

                if (sDateInMillisec != 0) {
                    toDateDialog.getDatePicker().setMinDate(sDateInMillisec);
                }
                if (fromDate != null) {
                    toDateDialog.show();
                } else {
                    Toast.makeText(context, "Please select start date first.", Toast.LENGTH_SHORT).show();
                    break;
                }
                break;

           /* case R.id.fabBtn:
                ShowHeadCollectionData();
                break;*/
        }
    }
}
