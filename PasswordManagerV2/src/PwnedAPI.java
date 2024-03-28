import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;

public class PwnedAPI {
	private static final String HIBP_API_URL = "https://api.pwnedpasswords.com/range/";

	public static boolean isPasswordPwned(String password) {
		OkHttpClient client = new OkHttpClient();
		try {
			String hashedPassword = DigestUtils.sha1Hex(password).toUpperCase();
			String prefix = hashedPassword.substring(0, 5);
			String suffix = hashedPassword.substring(5);

			String url = HIBP_API_URL + prefix;

			Request request = new Request.Builder().url(url).build();

			Response response = client.newCall(request).execute();
			if (response.isSuccessful()) {
				String responseBody = response.body().string();
				return responseBody.contains(suffix);
			} else {
				System.out.println("Error: " + response.code() + " - " + response.message());
				return false;
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
