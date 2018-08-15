public class WebService extends AsyncTask< String, String, String > {
    DataDownloaderListener dataDownloaderListener;
    private static String URL_WEB = "http://gwldx.com/api/loginvalidate.php?";

    protected void onPreExecute() {

    }

    public void setDataDownloaderListener(DataDownloaderListener dataDownloaderListener){
        this.dataDownloaderListener = dataDownloaderListener;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        if(result != null)
            dataDownloaderListener.dataDownloadedSuccessfully(result);
        else
            dataDownloaderListener.dataDownloadFailed();

    }

    @Override
    protected String doInBackground(String... params) {
        String dataParam = params[0];
        String response = "";

        try{
            URL url = new URL(URL_WEB);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(dataParam);
            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK){
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));

                while ((line=br.readLine()) != null) {
                    response += line;
                }

            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return response;
    }

    public static interface DataDownloaderListener{
        void dataDownloadedSuccessfully(String data);
        void dataDownloadFailed();
    }
}
