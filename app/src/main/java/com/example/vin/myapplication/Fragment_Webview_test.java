package com.example.vin.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Fragment_Webview_test.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Fragment_Webview_test#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Webview_test extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {

    };
    RestService restService;
    ProgressDialog pDialog;
    private static final int PERMS_REQUEST_CODE = 123;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Fragment_Webview_test() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Webview_test.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Webview_test newInstance(String param1, String param2) {
        Fragment_Webview_test fragment = new Fragment_Webview_test();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment__webview_test, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onStart() {
        try {
            super.onStart();

            View v = getView();
            pDialog = new ProgressDialog(getContext());
           /*WebView  mWebView = (WebView) v.findViewById(R.id.webview);
            mWebView.loadUrl("http://139.59.63.181");

            // Enable Javascript
            WebSettings webSettings = mWebView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            // Force links and redirects to open in the WebView instead of in a browser
            mWebView.setWebViewClient(new WebViewClient());*/
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

            StrictMode.setThreadPolicy(policy);



            /*URL u = new URL("http://che.org.il/wp-content/uploads/2016/12/pdf-sample.pdf");
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            String fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            FileOutputStream f = new FileOutputStream(new File(fileName,"my.pdf"));


            InputStream in = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ( (len1 = in.read(buffer)) > 0 ) {
                f.write(buffer,0, len1);
            }

            f.close();

            //String myPdfUrl = "http://che.org.il/wp-content/uploads/2016/12/pdf-sample.pdf";
            String myPdfUrl = "http://13.126.122.12/MYSQLConnTest/?name=SI-00001&parameter=erp";
            String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
           // Log.i(TAG, "Opening PDF: " + url);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(url);*/

          /*  restService = new RestService("hi");
            pDialog.show();
            restService.getService().filesownload("SI-00001", "erp", new Callback<PDF>() {
                @Override
                public void success(PDF response, Response response2) {
                    pDialog.hide();
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    pDialog.hide();
                }
            });*/

            pDialog.show();
            ServerAPI api = ServerAPI.retrofit.create(ServerAPI.class);

            api.downlload("http://13.126.122.12/MYSQLConnTest/?name=SI-00001&parameter=erp").enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {



                       /* pDialog.hide();
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                        File path = Environment.getExternalStorageDirectory();
                        //File path = Environment.getExternalStorageDirectory()+"/Imagess";
                        File file = new File("lysten_final.png");
                        //FileOutputStream fileOutputStream = new FileOutputStream(file);
                       // IOUtils.write(response.body().bytes(), fileOutputStream);
                       // fileOutputStream.close();



                        WebView mWebView = (WebView) getView().findViewById(R.id.webview);
                        mWebView.getSettings().setJavaScriptEnabled(true);
                        //mWebView.loadUrl(file.getAbsolutePath());
                        mWebView.loadData(response.body().string(),"image/png", null);*/
                        pDialog.hide();
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

                        StrictMode.setThreadPolicy(policy);
                        if (hasPermissions()) {
                            // our app has permissions.
                            // makeFolder();
                            File path = Environment.getExternalStorageDirectory();
                            File file = new File(path, "lysten_final.pdf");
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                            IOUtils.write(response.body().bytes(), fileOutputStream);
                            fileOutputStream.close();


                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(file), "application/pdf");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            Intent intent1 = Intent.createChooser(intent, "Open With");
                            try {
                                startActivity(intent1);
                            } catch (ActivityNotFoundException ex) {
                                // Instruct the user to install a PDF reader here, or something
                                Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }

                            /*WebView mWebView = (WebView) getView().findViewById(R.id.webview);
                            mWebView.getSettings().setJavaScriptEnabled(true);
                            String url = "http://docs.google.com/gview?embedded=true&url=file:///"+file.getAbsolutePath();
                            mWebView.loadUrl(url);*/

                        } else {
                            //our app doesn't have permissions, So i m requesting permissions.
                            requestPerms();
                        }


                    } catch (Exception ex) {
                        Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    pDialog.hide();
                }
            });





           /* String myPdfUrl = "http://che.org.il/wp-content/uploads/2016/12/pdf-sample.pdf";

            String url = "http://docs.google.com/gview?embedded=true&url=" + myPdfUrl;
            // Log.i(TAG, "Opening PDF: " + url);
            mWebView.getSettings().setJavaScriptEnabled(true);
            mWebView.loadUrl(url);*/


        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean hasPermissions() {
        int res = 0;
        //string array of permissions,
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        for (String perms : permissions) {
            res = getContext().checkCallingOrSelfPermission(perms);
            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    private void requestPerms() {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            boolean allowed = true;

            switch (requestCode) {
                case PERMS_REQUEST_CODE:

                    for (int res : grantResults) {
                        // if user granted all permissions.
                        allowed = allowed && (res == PackageManager.PERMISSION_GRANTED);
                    }

                    break;
                default:
                    // if user not granted permissions.
                    allowed = false;
                    break;
            }

            if (allowed) {
                //user granted all permissions we can perform our task.
                makeFolder();
            } else {
                // we will give warning to user that they haven't granted permissions.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                        Toast.makeText(getContext(), "Storage Permissions denied.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void makeFolder() {
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "EDABlogs");

            if (!file.exists()) {
                Boolean ff = file.mkdir();
                if (ff) {
                    Toast.makeText(getContext(), "Folder created successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to create folder", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(getContext(), "Folder already exist", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getContext(), ex.getMessage().toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
