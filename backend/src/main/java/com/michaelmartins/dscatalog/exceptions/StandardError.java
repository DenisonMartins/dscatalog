package com.michaelmartins.dscatalog.exceptions;

import java.time.Instant;

public class StandardError {

    private Instant timestamp = Instant.now();
    private Integer status;
    private String error;
    private String message;
    private String path;

    public StandardError() {
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public static final class Builder {
        private Instant timestamp = Instant.now();
        private Integer status;
        private String error;
        private String message;
        private String path;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder timestamp(Instant timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder status(Integer status) {
            this.status = status;
            return this;
        }

        public Builder error(String error) {
            this.error = error;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public StandardError build() {
            StandardError standardError = new StandardError();
            standardError.message = this.message;
            standardError.path = this.path;
            standardError.status = this.status;
            standardError.error = this.error;
            standardError.timestamp = this.timestamp;
            return standardError;
        }
    }
}
