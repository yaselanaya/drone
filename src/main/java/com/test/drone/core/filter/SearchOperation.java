package com.test.drone.core.filter;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public enum SearchOperation {
    EQUALITY {
        @Override
        public String sqlOperator() {
            return " = ";
        }
    },
    NEGATION {
        @Override
        public String sqlOperator() {
            return " <> ";
        }

        @Override
        public boolean isBinaryOperator() {
            return false;
        }
    },
    GREATER_THAN {
        @Override
        public String sqlOperator() {
            return " > ";
        }
    },
    LESS_THAN {
        @Override
        public String sqlOperator() {
            return " < ";
        }
    },
    GREATER_THAN_EQUAL {
        @Override
        public String sqlOperator() {
            return " >= ";
        }
    },
    LESS_THAN_EQUAL {
        @Override
        public String sqlOperator() {
            return " <= ";
        }
    },
    LIKE {
        @Override
        public String sqlOperator() {
            return " LIKE ";
        }
    },
    STARTS_WITH {
        @Override
        public String sqlOperator() {
            return null;
        }
    },
    ENDS_WITH {
        @Override
        public String sqlOperator() {
            return null;
        }
    },
    CONTAINS {
        @Override
        public String sqlOperator() {
            return null;
        }
    },
    NUMBER_LESS_EQUAL_THAN {
        @Override
        public String sqlOperator() {
            return " <= ";
        }
    },
    NUMBER_GREATER_EQUAL_THAN {
        @Override
        public String sqlOperator() {
            return " >= ";
        }
    },
    COMPARABLE_LESS_EQUAL_THAN {
        @Override
        public String sqlOperator() {
            return " <= ";
        }
    },
    COMPARABLE_GREATER_EQUAL_THAN {
        @Override
        public String sqlOperator() {
            return " >= ";
        }
    },
    DATE_GREATER_EQUAL_THAN {
        @Override
        public String sqlOperator() {
            return " >= ";
        }
    },
    DATE_LESS_EQUAL_THAN {
        @Override
        public String sqlOperator() {
            return " <= ";
        }
    },
    IN {
        @Override
        public String sqlOperator() {
            return " IN ";
        }
    },
    NOT_IN {
        @Override
        public String sqlOperator() {
            return " NOT IN ";
        }
    },
    IS_NULL {
        @Override
        public String sqlOperator() {
            return " IS NULL ";
        }

        @Override
        public boolean isBinaryOperator() {
            return false;
        }

        @Override
        public boolean isOperatorStart() {
            return false;
        }
    },
    NOT_NULL {
        @Override
        public String sqlOperator() {
            return " IS NOT NULL ";
        }

        @Override
        public boolean isBinaryOperator() {
            return false;
        }

        @Override
        public boolean isOperatorStart() {
            return false;
        }
    },
    IS_EMPTY {
        @Override
        public String sqlOperator() {
            return null;
        }
    },
    IS_NULL_OR_EMPTY {
        @Override
        public String sqlOperator() {
            return null;
        }

        @Override
        public Set<SearchOperation> operations() {
            return new HashSet<>(){{
                add(IS_NULL);
                add(IS_EMPTY);
            }};
        }

        @Override
        public boolean isComplexOperation() {
            return true;
        }
    },
    IS_NOT_EMPTY {
        @Override
        public String sqlOperator() {
            return null;
        }
    },
    IS_NULL_OR_ZERO {
        public String sqlOperator() {
            return null;
        }

        @Override
        public Set<SearchOperation> operations() {
            return new HashSet<>(){{
                add(IS_NULL);
                add(IS_ZERO);
            }};
        }

        @Override
        public boolean isComplexOperation() {
            return true;
        }
    },
    IS_ZERO {
        @Override
        public String sqlOperator() {
            return " = ";
        }
    },
    NATIVE_QUERY {
        @Override
        public String sqlOperator() {
            return null;
        }
    };

    public static SearchOperation from(String operation) {
        return Arrays.stream(SearchOperation.values())
                .filter(searchOperation -> searchOperation.name().equalsIgnoreCase(operation)).findFirst()
                .orElse(SearchOperation.EQUALITY);
    }

    public abstract String sqlOperator();

    public boolean isBinaryOperator() {
        return true;
    }

    public boolean isOperatorStart() {
        return true;
    }

    public boolean isComplexOperation() {
        return false;
    }

    public Set<SearchOperation> operations() {
        return Collections.emptySet();
    }
}
