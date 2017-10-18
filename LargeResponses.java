package com.company;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LargeResponses {

    private class Pair<T>{
        private T fst;

        private T snd;

        public Pair(T fst, T snd) {
            this.fst = fst;
            this.snd = snd;
        }
    }

    private class Response {
        private String hostName;
        private String unknown1;
        private String unknown2;
        private String timeStamp;
        private String request;
        private String responseCode;
        private String bytes;

        public Response(
                String hostName,
                String unknown1,
                String unknown2,
                String timeStamp,
                String request,
                String responseCode,
                String bytes) {
            this.hostName = hostName;
            this.unknown1 = unknown1;
            this.unknown2 = unknown2;
            this.timeStamp = timeStamp;
            this.request = request;
            this.responseCode = responseCode;
            this.bytes = bytes;
        }
    }
    private boolean isFileInRange(String fileName) throws IOException {
        LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(new File(fileName)));
        boolean isInRange = BigInteger.valueOf(lineNumberReader.getLineNumber() + 1).compareTo(BigInteger.valueOf(2).pow(5)) <= 0;
        lineNumberReader.close();
        return isInRange;
    }

    private String readFile(String fileName) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    private boolean isByteInRange(String bytes) {
        return BigInteger.valueOf(Long.parseLong(bytes)).compareTo(BigInteger.valueOf(10).pow(12)) <= 0;
    }

    private List<Response> responseParser(String fileName) throws IOException {
        return Stream.of(readFile(fileName).split("\n"))
                .map(l -> {
                    String hostname = l.split("-")[0];
                    String timeStamp = l.split(" - - ")[1].split("] ")[0] + "]";
                    String[] rest = l.split(" - - ")[1].split("] ")[1].split(" ");
                    String request = rest[0] + rest[1];
                    String responseCode = rest[2];
                    String bytes = rest[3];
                    if(BigInteger.valueOf(Long.valueOf(bytes)).compareTo(BigInteger.valueOf(10).pow(12)) > 0) {
                        try {
                            throw new Exception("out of range");
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        return null;
                    }
                    return new Response(hostname, "-", "-", timeStamp, request, responseCode, bytes);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public Pair<Long> largeResponse(String fileName) throws IOException {
        List<Response> responses = responseParser(fileName).stream()
                .filter(r -> Long.valueOf(r.bytes).compareTo(5000L) > 0)
                .collect(Collectors.toList());

        return new Pair<Long>((long) responses.size(), responses.stream()
                .map(r -> Long.valueOf(r.bytes))
                .reduce(0L, (b1, b2) -> (b1 + b2)));

    }

    public static void main(String[] args) throws IOException {
        LargeResponses largeResponses = new LargeResponses();
        System.out.println(largeResponses.readFile("src/com/company/response.txt"));
        System.out.println(largeResponses.isFileInRange("src/com/company/response.txt"));
        System.out.println(largeResponses.isByteInRange("500"));
        System.out.println(largeResponses.responseParser("src/com/company/response.txt"));
        System.out.println(largeResponses.largeResponse("src/com/company/response.txt"));
    }
}
