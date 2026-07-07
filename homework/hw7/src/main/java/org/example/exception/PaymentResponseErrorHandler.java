package org.example.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class PaymentResponseErrorHandler implements ResponseErrorHandler {

    private final ObjectMapper objectMapper;

    public PaymentResponseErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().is4xxClientError()){
            ExecutorPaymentErrorResponse executorPaymentErrorResponse =
                    objectMapper.readValue(response.getBody(), ExecutorPaymentErrorResponse.class);

            throw new PaymentServiceException(executorPaymentErrorResponse.message());
        }
    }
}
