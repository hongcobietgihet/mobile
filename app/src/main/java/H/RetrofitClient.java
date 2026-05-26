//package H;
//
//public class RetrofitClient {
//    private static final String BASE_URL =
//            "http://blackntt.net:88/";
//
//    private static Retrofit retrofit;
//
//    public static Retrofit getClient() {
//
//        if (retrofit == null) {
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(
//                            GsonConverterFactory.create()
//                    )
//                    .build();
//        }
//
//        return retrofit;
//    }
//}
