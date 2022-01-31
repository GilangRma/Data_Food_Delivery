package com.dimata.demo.user.demo_data_user.core.api;
// package com.dimata.demo.sekolah.demo_data_siswa.core.api;

// import java.time.Duration;

// import com.dimata.demo.sekolah.demo_data_siswa.core.auth.ServiceTokenFactory;
// import com.dimata.demo.sekolah.demo_data_siswa.core.auth.authentication.JwsAuthenticationConverter;
// import com.dimata.demo.sekolah.demo_data_siswa.exception.ExceptionMessage;
// import com.dimata.demo.sekolah.demo_data_siswa.exception.ServiceException;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.MediaType;
// import org.springframework.web.reactive.function.client.ClientRequest;
// import org.springframework.web.reactive.function.client.ClientResponse;
// import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
// import org.springframework.web.reactive.function.client.WebClient;
// import org.springframework.web.reactive.function.client.WebClient.Builder;

// import lombok.extern.slf4j.Slf4j;
// import reactor.core.publisher.Mono;
// import reactor.util.retry.Retry;
// import reactor.util.retry.RetryBackoffSpec;

// /**
//  * abstraksi dari webclient untuk memudahkan dalam pembuatan service communication client.
//  * Ektends class ini jika ingin membuat komunikasi servis client yang baru.
//  * 
//  * @author Hariyogi.
//  */
// @Slf4j
// public abstract class ServiceCommBase {

//     protected static final String SERVICE_TOKEN_HEADER = JwsAuthenticationConverter.SERVICE_AUTH;
//     protected static final long MAXIMUM_COMMUNICATION_RETRY_ATTEMPTS = 3;
//     protected static final Duration MIN_BACKOFF_TIME = Duration.ofMillis(500);

//     protected Mono<WebClient> createWebClientInstance() {
//         return Mono.zip(getAccessToken(), Mono.just(WebClient.builder().baseUrl(getBaseUrl())))
//             .map(z -> z.getT2()
//                 .defaultHeader(JwsAuthenticationConverter.SERVICE_AUTH, z.getT1())
//                 .filter(logRequest())
//             )
//             .map(Builder::build);
//     }

//     protected Mono<String> getAccessToken() {
//         return getServiceTokenFactory().createAccessToken(getCode(), null)
//             .map(jws -> "Bearer " + jws.getAccessToken());
//     }

//     protected Mono<ServiceException> handleException(ClientResponse response) {
//         var isJson = response.headers()
//             .contentType()
//             .filter(ct -> ct.equals(MediaType.APPLICATION_JSON))
//             .isPresent();

//             if(isJson) {
//                 return response.bodyToMono(ExceptionMessage.class)
//                     .flatMap(e -> Mono.error(buildException(e.getMessage(), response.statusCode())));
//             }else {
//                 return response.createException()
//                     .flatMap(e -> Mono.error(buildException(e.getMessage(), response.statusCode())));
//             }
//     }

//     private ServiceException buildException(String message, HttpStatus status) {
//         var errorMessage = new StringBuilder()
//             .append("There is problem with ")
//             .append(getCode()).append(" service. Cause : ")
//             .append(message)
//             .toString();

//         return new ServiceException(errorMessage, status);
//     }

//     protected RetryBackoffSpec handleRetyCommunication() {
//         return Retry.backoff(getMaxRetryAttempts(), getMinBackoffTime())
//             .filter(this::checkIsNeedRetry)
//             .onRetryExhaustedThrow((signal, throwable) -> 
//                 getExceptionWhenExhausted(throwable.failure().getMessage())
//             );
//     }

//     private ServiceException getExceptionWhenExhausted(String errorMessage) {
//         return new ServiceException(getErrorMessageWhenExhausted(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
//     }

//     private String getErrorMessageWhenExhausted(String errorMessage) {
//         return new StringBuilder()
//             .append("Failed communicated with service ")
//             .append(getCode())
//             .append(" even trying ")
//             .append(getMaxRetryAttempts())
//             .append(" attemps. Cause : ")
//             .append(errorMessage)
//             .toString();
//     }

//     protected long getMaxRetryAttempts() {
//         return MAXIMUM_COMMUNICATION_RETRY_ATTEMPTS;
//     }

//     protected Duration getMinBackoffTime() {
//         return MIN_BACKOFF_TIME;
//     }

//     protected boolean checkIsNeedRetry(Throwable ex) {
//         var isNeedRetry = false;

//         if(ex instanceof ServiceException) {
//             var serviceException = (ServiceException) ex;
//             isNeedRetry = serviceException.getStatus().is5xxServerError();
//         }

//         return isNeedRetry;
//     }

//     private ExchangeFilterFunction logRequest() {
//         return ExchangeFilterFunction.ofRequestProcessor(req -> {
//             logMethodAndUrl(req);
//             logHeaders(req);
//             return Mono.just(req);
//         });
//     }

//     private void logHeaders(ClientRequest request) {
//         request.headers()
//             .forEach((name, values) -> {
//                 values.forEach(value -> {
//                     log.debug("{}={}", name, value);
//                 });
//             });
//     }

//     private void logMethodAndUrl(ClientRequest request) {
//         var sb = new StringBuilder();
//         sb.append(request.method().name());
//         sb.append(" to ");
//         sb.append(request.url());
//         log.debug(sb.toString());
//     }

//     protected abstract String getCode();
//     protected abstract String getBaseUrl();
//     protected abstract ServiceTokenFactory getServiceTokenFactory();
// }
