package com.example.kuangkuang.data;

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
public class AndroidResult<T> {
    // hide the private constructor to limit subclass types (Success, Error)
    private AndroidResult() {
    }

    @Override
    public String toString() {
        if (this instanceof AndroidResult.Success) {
            AndroidResult.Success success = (AndroidResult.Success) this;
            return "Success[data=" + success.getData().toString() + "]";
        } else if (this instanceof AndroidResult.Error) {
            AndroidResult.Error error = (AndroidResult.Error) this;
            return "Error[exception=" + error.getError().toString() + "]";
        }
        return "";
    }

    // Success sub-class
    public final static class Success<T> extends AndroidResult {
        private T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    // Error sub-class
    public final static class Error extends AndroidResult {
        private Exception error;

        public Error(Exception error) {
            this.error = error;
        }

        public Exception getError() {
            return this.error;
        }
    }
}