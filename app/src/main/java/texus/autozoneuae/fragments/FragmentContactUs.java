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

package texus.autozoneuae.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import texus.autozoneuae.R;
import texus.autozoneuae.controls.ContactBlanRow;
import texus.autozoneuae.controls.ContactMainText;
import texus.autozoneuae.controls.ContactSubText;



public class FragmentContactUs extends Fragment {


    public static final String LINK_TWITTER = "https://twitter.com/SABEESHAUTOZONE";
    public static final String LINK_FACEBOOK = "https://www.facebook.com/autozonearmor/";
    public static final String LINK_GOOGLE_PLUS = "https://plus.google.com/108072810371953685189";
    public static final String LINK_INSTAGRAM = "https://www.instagram.com/autozoneuae";
    public static final String LINK_YOUTUBE = "https://youtu.be/hN-Fu9ZpdUI";

    public static final String LINK_LINKED_IN = "https://www.linkedin.com/company/2967546?trk=" +
            "vsrp_companies_res_name&trkInfo=VSRPsearchId%3A1496488871479968026135%2CVSRPtargetId" +
            "%3A2967546%2CVSRPcmpt%3Aprimary";

    public static final String LINK_MAP = "https://www.google.com/maps/place/AUTO+ZONE+ARMOR+%26+PROCESSING+CARS+L.L.C/@25.402155,55.499052,15z/data=!4m13!1m7!3m6!1s0x0:0x8f719cf68913f5d2!2sAUTO+ZONE+ARMOR+%26+PROCESSING+CARS+L.L.C!3b1!8m2!3d25.402155!4d55.499052!3m4!1s0x0:0x8f719cf68913f5d2!8m2!3d25.402155!4d55.499052?hl=en-US";



    LinearLayout llContactHolder;
    LinearLayout llSocialMediaHolder;

    ImageView imFacebook;
    ImageView imInstagram;
    ImageView imGooglePlus;
    ImageView imTwitter;
    ImageView imYouTube;
    ImageView imLinkedin;

//    TextView tvFacebook;
//    TextView tvInstagram;
//    TextView tvGooglePlus;
//    TextView tvTwitter;
//    TextView tvYouTube;

    ImageView imDirection;
    TextView tvDirection;


    private Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_contact, container, false);

        context = getActivity();

        llContactHolder = (LinearLayout) view.findViewById(R.id.llContactHolder) ;
        llSocialMediaHolder = (LinearLayout) view.findViewById(R.id.llSocialMediaHolder);

        llContactHolder.addView(new ContactSubText(context,"AUTOZONE Armor & Processing Cars LLC",ContactSubText.TYPE_NONE ));
        llContactHolder.addView( new ContactSubText(context,"Office No: D2-D3",ContactSubText.TYPE_NONE));
        llContactHolder.addView( new ContactSubText(context,"Shaikh Rashid Bin Abdul Aziz Aaemi Street,",
                ContactSubText.TYPE_NONE));
        llContactHolder.addView( new ContactSubText(context,"Jurf Ajman, UAE - 4029",
                ContactSubText.TYPE_NONE));
        llContactHolder.addView( new ContactSubText(context,"+971 6 7478 965",ContactSubText.TYPE_PHONE));
        llContactHolder.addView( new ContactSubText(context,"+971 6 7433 458",ContactSubText.TYPE_FAX));
        llContactHolder.addView( new ContactSubText(context,"info@autozoneuae.com",ContactSubText.TYPE_EMAIL));


        View child = inflater.inflate(R.layout.element_direction, null);
        llContactHolder.addView( child);

        imDirection = (ImageView) child.findViewById(R.id.imMap);
        tvDirection = (TextView) child.findViewById(R.id.tvGetDirection);



        llContactHolder.addView( new ContactBlanRow(context));

//        llContactHolder.addView(new ContactMainText(context,""));
//        llContactHolder.addView( new ContactSubText(context,"",ContactSubText.TYPE_NONE));
//        llContactHolder.addView( new ContactSubText(context,"",ContactSubText.TYPE_NONE));
//        llContactHolder.addView( new ContactSubText(context,"",ContactSubText.TYPE_NONE));
//        llContactHolder.addView( new ContactSubText(context,"",ContactSubText.TYPE_NONE));
//        llContactHolder.addView( new ContactSubText(context,"",ContactSubText.TYPE_NONE));

        llContactHolder.addView(new ContactMainText(context,"AMBULANCES AND SPECIAL VEHICLES"));
        llContactHolder.addView( new ContactSubText(context,"SABEESH P BABU",ContactSubText.TYPE_NONE));
        llContactHolder.addView( new ContactSubText(context,"+971 50 9593559",ContactSubText.TYPE_WATSAPP));
        llContactHolder.addView( new ContactSubText(context,"cars@autozoneuae.com",ContactSubText.TYPE_EMAIL));
        llContactHolder.addView( new ContactBlanRow(context));

        llContactHolder.addView(new ContactMainText(context,"AMBULANCES AND ARMORED VEHICLES"));
        llContactHolder.addView( new ContactSubText(context,"ZEESHAN GONDAL ",ContactSubText.TYPE_NONE));
        llContactHolder.addView( new ContactSubText(context,"+971 55 7562284",ContactSubText.TYPE_WATSAPP));
        llContactHolder.addView( new ContactSubText(context,"contact@autozoneuae.com",ContactSubText.TYPE_EMAIL));
        llContactHolder.addView( new ContactBlanRow(context));

        llContactHolder.addView(new ContactMainText(context,"AUTO PARTS AND ARMORED VEHICLE PARTS"));
        llContactHolder.addView( new ContactSubText(context,"MUHAMMAD MAIRAJ KHALID  ",ContactSubText.TYPE_NONE));
        llContactHolder.addView( new ContactSubText(context,"+971 55 9518184",ContactSubText.TYPE_WATSAPP));
        llContactHolder.addView( new ContactSubText(context,"parts@autozoneuae.com",ContactSubText.TYPE_EMAIL));
        llContactHolder.addView( new ContactBlanRow(context));

        llContactHolder.addView(new ContactMainText(context,"AUTO PARTS AND 4×4 ACCESSORIES"));
        llContactHolder.addView( new ContactSubText(context,"A RAHMAN ZAFAR ",ContactSubText.TYPE_NONE));
        llContactHolder.addView( new ContactSubText(context,"+971 50 8739401",ContactSubText.TYPE_WATSAPP));
        llContactHolder.addView( new ContactSubText(context,"sales@autozoneuae.com",ContactSubText.TYPE_EMAIL));
        llContactHolder.addView( new ContactBlanRow(context));








        imFacebook = (ImageView) view.findViewById(R.id.imFacebook);
        imInstagram = (ImageView) view.findViewById(R.id.imInstagram);
        imGooglePlus = (ImageView) view.findViewById(R.id.imGooglePlus);
        imTwitter = (ImageView) view.findViewById(R.id.imTwitter);
        imYouTube = (ImageView) view.findViewById(R.id.imYouTube);
        imLinkedin = (ImageView) view.findViewById(R.id.imLinkedin);

//        tvFacebook = (TextView) view.findViewById(R.id.tvFacebook);
//        tvInstagram = (TextView) view.findViewById(R.id.tvInstagram);
//        tvGooglePlus = (TextView) view.findViewById(R.id.tvGooglePlus);
//        tvTwitter = (TextView) view.findViewById(R.id.tvTwitter);
//        tvYouTube = (TextView) view.findViewById(R.id.tvYouTube);


        imFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFacebook();
            }
        });

        imInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInstagram();
            }
        });

        imGooglePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGooglePlus();
            }
        });

        imTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTwitter();
            }
        });

        imYouTube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTube();
            }
        });

        imLinkedin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLinkedIn();
            }
        });

        imDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnMap();
            }
        });

        tvDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOnMap();
            }
        });

        return view;
    }

    private void showOnMap() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_MAP));;
        startActivity(intent);
    }

    private void openFacebook() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_FACEBOOK));;
        startActivity(intent);
    }

    private void openTwitter( ) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_TWITTER));;
        startActivity(intent);
    }

    private void openGooglePlus() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_GOOGLE_PLUS));;
        startActivity(intent);
    }

    private void openInstagram() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_INSTAGRAM));;
        startActivity(intent);
    }

    private void openYouTube() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_YOUTUBE));;
        startActivity(intent);
    }

    private void openLinkedIn() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINK_LINKED_IN));;
        startActivity(intent);
    }

}


