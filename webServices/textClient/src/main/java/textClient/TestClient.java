package textClient;

import textClient.TextCasingStub.TitleCaseWordsWithToken;
import textClient.TextCasingStub.TitleCaseWordsWithTokenResponse;

public class TestClient {

  public static void main(String[] args) {
    TextCasingStub stub;
    TitleCaseWordsWithToken query;
    TitleCaseWordsWithTokenResponse response;

    try {
      stub = new TextCasingStub(); // Creating client stub.

      query = new TitleCaseWordsWithToken(); // Building request.
      query.setSText("This is a cool title. It is not short, but nice.");
      query.setSToken(" ");

      response = stub.titleCaseWordsWithToken(query);
      System.out.println(response.getTitleCaseWordsWithTokenResult());

    } catch (Throwable f) {
      f.printStackTrace();
    }
  }
}
