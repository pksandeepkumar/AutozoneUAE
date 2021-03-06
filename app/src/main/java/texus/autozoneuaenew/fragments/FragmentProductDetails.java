/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package texus.autozoneuaenew.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import texus.autozoneuaenew.ApplicationClass;
import texus.autozoneuaenew.MainActivityNew;
import texus.autozoneuaenew.R;
import texus.autozoneuaenew.adapter.ImageViewPagerAdapter;
import texus.autozoneuaenew.controls.ColorRow;
import texus.autozoneuaenew.controls.HeadderRow;
import texus.autozoneuaenew.controls.NameValueRow;
import texus.autozoneuaenew.controls.SquirePagerView;
import texus.autozoneuaenew.datamodels.Product;
import texus.autozoneuaenew.datamodels.SpecData;
import texus.autozoneuaenew.dialogs.ProgressDialog;
import texus.autozoneuaenew.dialogs.SpecDialog;
import texus.autozoneuaenew.network.Downloader;
import texus.autozoneuaenew.network.NetworkService;
import texus.autozoneuaenew.utility.Utility;

public class FragmentProductDetails extends Fragment {

    public static final String PARAM_PRODUCT_DATA = "ParamProductData";

    SquirePagerView squirePagerView;
    ViewPager viewPager;
    Product product;

    Button btnDownloadPDF;
    Button btnGetAQuote;
    Button btnCallForEnquiry;
    Button btnSpecificaltion;

    LinearLayout llSpecHolder;
    TextView tvTitle;
    LinearLayout llDesccHolder;
    String pdfFileName = "";



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = (View) inflater.inflate(
                R.layout.fragment_product_details, container, false);

        product = (Product) getArguments().get(PARAM_PRODUCT_DATA);

        if( product != null) {
            initViews(view);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivityNew.setDisableBackButton();
    }

    private void initViews(View view) {

        squirePagerView = (SquirePagerView) view.findViewById(R.id.squirePagerView);
        viewPager = squirePagerView.viewPager;

        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvTitle.setText( product.product_name);

        ImageViewPagerAdapter adapter = new ImageViewPagerAdapter(getActivity(), product.image_urls);
        viewPager.setAdapter(adapter);


        CirclePageIndicator indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);
        indicator.setFillColor(Color.parseColor("#FFFFFF"));
        indicator.setStrokeColor(Color.parseColor("#FFFFFF"));
        indicator.setRadius(indicator.getRadius() * 1f);
        indicator.setViewPager(viewPager);

        llSpecHolder = (LinearLayout) view.findViewById(R.id.llSpecHolder);

        HeadderRow headderRow = new HeadderRow(getActivity(), "");
        llSpecHolder.addView(headderRow);

        LoadProductSpecData task = new LoadProductSpecData(getActivity(), headderRow);
        task.execute();

        MainActivityNew.setEnableBackButton();

//        loadProductDetails();
    }

    public void publishSpec(ArrayList<SpecData> specDatas) {
        if (specDatas == null) {
            Log.e("ProductDetailActivity", "Specs Is null");
            return;
        }

        Log.e("ProductDetailActivity", "Spec Data Size:" + specDatas.size());

        llSpecHolder.removeAllViews();

        HeadderRow headderRow = new HeadderRow(getActivity(), "");
        llSpecHolder.addView(headderRow);

        int count = 0;
        for (SpecData specData : specDatas) {
            if (specData.type == SpecData.SPEC_TYPE_NORMAL) {
                NameValueRow row = new NameValueRow(getActivity(), specData.name, specData.value);
                llSpecHolder.addView(row);
            } else if (specData.type == SpecData.SPEC_TYPE_COLOR) {
                ColorRow row = new ColorRow(getActivity(), specData.name, specData.colors);
                llSpecHolder.addView(row);
            }
            count++;
//            if (count < specDatas.size()) {
//                LineRow row = new LineRow(getActivity());
//                llSpecHolder.addView(row);
//            }
        }

        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View child = inflater.inflate(R.layout.element_buttons, null);
        llSpecHolder.addView(child);

        btnDownloadPDF      = (Button) child.findViewById(R.id.btnDownloadPDF) ;
        btnGetAQuote        = (Button) child.findViewById(R.id.btnGetAQuote) ;
        btnCallForEnquiry   = (Button) child.findViewById(R.id.btnCallForEnquiry) ;
        btnSpecificaltion   = (Button) child.findViewById(R.id.btnSpecificaltion) ;

        btnDownloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermissionAndDownload();
            }
        });

        btnGetAQuote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
//                GetAQuoteDialog dialog = new GetAQuoteDialog(getActivity(), product);
//                dialog.show();
            }
        });

        btnCallForEnquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialNumber("+971557562284");
            }
        });

        btnSpecificaltion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpecDialog dialog = new SpecDialog(getActivity(), product);
                dialog.show();
            }
        });
    }


    private void dialNumber( String number ) {
        if (ContextCompat.checkSelfPermission( getActivity(),
                Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            String uri = "tel:" + number ;
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse(uri));
            getActivity().startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CALL_PHONE},
                    1242);
        }
    }



    public void checkPermissionAndDownload () {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                12);
            return;
        }

        LoadAndViewPDF task = new LoadAndViewPDF(getActivity());
        task.execute();


    }

    public void showPdf() {
        File file = new File(ApplicationClass.PDF_FOLDER + File.separator + pdfFileName);
        PackageManager packageManager = ApplicationClass.getInstance().getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);
    }


    public class LoadProductSpecData extends AsyncTask<Void, Void, Void> {

        Context context;
        ArrayList<SpecData> specDatas = null;
        HeadderRow headderRow;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            headderRow.setTitle("Specifications" + "( Loading....)");
        }

        public LoadProductSpecData(Context context, HeadderRow headderRow) {
            this.context = context;
            this.headderRow = headderRow;
        }

        @Override
        protected Void doInBackground(Void... params) {

            String resp = Utility.getData(SpecData.FILE_NAME_START + product.product_id);
            if (resp.length() != 0) {
                Log.e("ProductDetailActivity", "OFFLINE DATA");
                specDatas = SpecData.getParesed(resp);
                publishProgress();

            }

            NetworkService.getAndSave(ApplicationClass.URL_GET_PRODUCT_SPEC + product.product_id,
                    SpecData.FILE_NAME_START + product.product_id);

            String resp2 = Utility.getData(SpecData.FILE_NAME_START + product.product_id);

            if (!resp.equals(resp2)) {
                specDatas = SpecData.getParesed(resp2);
                publishProgress();
            } else {
                Log.e("ProductDetailActivity", "Response are same!!!!");
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Log.e("ProductDetailActivity", "Calling publish!!!!");
            publishSpec(specDatas);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }


    }

    public void sendMail( ) {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","cars@autozoneuae.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Enquire about " + product.product_name );
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");
        getActivity().startActivity(Intent.createChooser(emailIntent, "Send email to AutozoneUAE"));

    }


    public class LoadAndViewPDF extends AsyncTask<Void, Void, Void> {
        Context context;
        boolean status;
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(context);
            dialog.show();

        }

        public LoadAndViewPDF(Context context) {
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            File folder = new File(ApplicationClass.PDF_FOLDER);
            File file = new File(folder, pdfFileName);
            try {
                file.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            String pdfURL = NetworkService.get(ApplicationClass.URL_GET_PRODUCT_PDF + product.product_id);
            if (pdfURL.trim().length() != 0) {
                status = Downloader.DownloadFile(pdfURL, file);
            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            dialog.hide();
            if (status) {
                showPdf();
            } else {
                Toast.makeText(context, "No pdf available!!", Toast.LENGTH_LONG).show();
            }

        }

    }


}
