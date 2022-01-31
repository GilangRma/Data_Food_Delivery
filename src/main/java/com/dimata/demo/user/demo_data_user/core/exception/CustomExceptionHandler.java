package com.dimata.demo.user.demo_data_user.core.exception;
// package com.dimata.demo.sekolah.demo_data_siswa.core.exception;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.annotation.Profile;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.http.server.reactive.ServerHttpRequest;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;
// import org.springframework.web.server.ServerWebInputException;

// import com.dimata.demo.sekolah.demo_data_siswa.core.log.Log;
// import com.dimata.demo.sekolah.demo_data_siswa.core.log.LogDbHandler;

// import reactor.core.publisher.Mono;
// import reactor.util.function.Tuple2;

// /**
//  * Rest Controller Advice merupakan global exception. Jika exception terjadi di RestController dan tidak
//  * di handle langsung, maka Advice ini yang akan menghandle exception tersebut.
//  *
//  * @author Hariyogi
//  * @since 2 Sep 2020
//  */
// @RestControllerAdvice
// @Profile({"prod", "staging"})
// public class CustomExceptionHandler {

//     @Autowired
//     private LogDbHandler logDbHandler;

//     @ExceptionHandler({DataNotFoundException.class})
//     public Mono<ResponseEntity<ExceptionMessage>> handleNotFoundException(RuntimeException exception,
//                                                                           ServerHttpRequest request) {
//         return Mono.zip(Mono.just(exception), Mono.just(request))
//                 .flatMap(this::saveLogRecord)
//                 .map(result -> createResponse(result, HttpStatus.NOT_FOUND));
//     }

//     @ExceptionHandler({ServerWebInputException.class})
//     public Mono<ResponseEntity<ExceptionMessage>> handleBadRequestException(RuntimeException exception,
//                                                                             ServerHttpRequest request) {
//         return Mono.zip(Mono.just(exception), Mono.just(request))
//                 .flatMap(this::saveLogRecord)
//                 .map(result -> createResponse(result, HttpStatus.BAD_REQUEST));
//     }

//     @ExceptionHandler({DataAlreadyExistException.class})
//     public Mono<ResponseEntity<ExceptionMessage>> handleConflictException(RuntimeException exception,
//                                                                           ServerHttpRequest request) {
//         return Mono.zip(Mono.just(exception), Mono.just(request))
//                 .flatMap(this::saveLogRecord)
//                 .map(result -> createResponse(result, HttpStatus.CONFLICT));
//     }

//     @ExceptionHandler({UnauthorizedAccessException.class})
//     public Mono<ResponseEntity<ExceptionMessage>> handleUnauthorizedException(RuntimeException exception,
//                                                                               ServerHttpRequest request) {
//         return Mono.zip(Mono.just(exception), Mono.just(request))
//                 .flatMap(this::saveLogRecord)
//                 .map(result -> createResponse(result, HttpStatus.UNAUTHORIZED));
//     }


//     @ExceptionHandler({Exception.class})
//     @Profile("prod")
//     public Mono<ResponseEntity<ExceptionMessage>> handleUnknownError(Exception exception,
//                                                                      ServerHttpRequest request) {
//         return Mono.zip(Mono.just(exception), Mono.just(request))
//                 .flatMap(this::saveLogRecordException)
//                 .map(result -> createResponse(result, HttpStatus.INTERNAL_SERVER_ERROR));
//     }

//     private Mono<Log> saveLogRecord(Tuple2<RuntimeException, ServerHttpRequest> zip) {
//         return logDbHandler.create(
//                 zip.getT2(),
//                 (zip.getT1().getMessage() == null) ? "Null" : zip.getT1().getMessage(),
//                 LogDbHandler.LogStatus.ERROR,
//                 zip.getT1()
//         );
//     }

//     private Mono<Log> saveLogRecordException(Tuple2<Exception, ServerHttpRequest> zip) {
//         return logDbHandler.create(
//                 zip.getT2(),
//                 (zip.getT1().getMessage() == null) ? "Null" : zip.getT1().getMessage(),
//                 LogDbHandler.LogStatus.ERROR,
//                 zip.getT1()
//         );
//     }


//     private ResponseEntity<ExceptionMessage> createResponse(Log logTable, HttpStatus status) {
//         ExceptionMessage message = new ExceptionMessage(
//                 logTable.getTicket(),
//                 logTable.getStatus(),
//                 logTable.getHttpCode(),
//                 logTable.getPath(),
//                 logTable.getMessage(),
//                 ""
//         );
//         return new ResponseEntity<>(message, status);
//     }


// }
