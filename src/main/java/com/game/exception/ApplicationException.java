package com.game.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ApplicationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ApplicationCode applicationCode;
    private String description;
    private List<ApplicationException> nestedExceptions = new ArrayList<>(  );

    public ApplicationException() {
        super( "Internal Server Error" );
    }

    public ApplicationException(String message) {
        super( message );
        applicationCode = new ApplicationCodeImpl( "500", message );
    }

    public ApplicationException(ApplicationCode applicationCode) {
        super( applicationCode.message() );
        this.applicationCode = applicationCode;
    }

    public ApplicationException(ApplicationCode applicationCode, String description) {
        this( applicationCode );
        this.description = description;
    }

    public ApplicationException(String errorCode, String message, String description) {
        this( new ApplicationCodeImpl( errorCode, message ), description );
    }

    public ApplicationException(ApplicationCode applicationCode, Throwable cause) {
        super( cause );
        this.applicationCode = applicationCode;
    }

    public ApplicationException(String message, Throwable cause) {
        this( new ApplicationCodeImpl( "500", message ), cause );
    }

    public void addNestedException(ApplicationException e) {
        nestedExceptions.add( e );
    }

    public List<ApplicationException> getNestedExceptions() {
        return nestedExceptions;
    }

    public ApplicationCode getApplicationCode() {
        return applicationCode;
    }

    public String getDescription() {
        return description;
    }

    static class ApplicationCodeImpl implements ApplicationCode {

        private String code;
        private String message;

        public ApplicationCodeImpl(String code, String message) {
            this.code = code;
            this.message = message;
        }

        @Override
        public String code() {
            return code;
        }

        @Override
        public String message() {
            return message;
        }

    }

    public static class Builder {

        private ApplicationException exception;
        private List<ApplicationException> nested = new ArrayList<>(  );

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder root(ApplicationCode code) {
            return this.root( code, null );
        }

        public Builder root(ApplicationCode code, String descr) {
            this.exception = new ApplicationException( code, descr );
            return this;
        }

        public Builder error(ApplicationCode code) {
            return this.error( code, null );
        }

        public Builder error(ApplicationCode code, String descr) {
            this.nested.add( new ApplicationException( code, descr ) );
            return this;
        }

        public boolean isErrorExist() {
            return exception != null || nested.size() > 0;
        }

        public Optional<ApplicationException> build() {
            if (!isErrorExist()) return Optional.empty();
            if (nested.size() == 0) return Optional.ofNullable(exception);
            if (exception == null && nested.size() == 1) return Optional.of(nested.iterator().next());
            if (exception == null) {
                StringBuilder stringBuilder = new StringBuilder();
                nested.stream().forEach(n -> stringBuilder.append(n.getMessage()).append(" : "));
                this.exception = new ApplicationException(stringBuilder.toString());
            }
            this.exception.nestedExceptions = nested;
            return Optional.of(exception);
        }
    }
}