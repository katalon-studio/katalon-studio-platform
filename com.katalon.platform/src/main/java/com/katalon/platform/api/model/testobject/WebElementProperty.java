package com.katalon.platform.api.model.testobject;

public interface WebElementProperty {

    String getName();

    String getValue();
    
    boolean isSelected();

    String getMatchCondition();
    
    public enum MATCH_CONDITION {
        EQUAL("equals"), NOT_EQUAL("not equal"), CONTAINS("contains"), NOT_CONTAIN("not contain"), STARTS_WITH(
                "starts with"), ENDS_WITH("ends with"), MATCH_REGEX("matches regex"), NOT_MATCH_REGEX("not match regex");

        private String text;

        private MATCH_CONDITION(String value) {
            this.text = value;
        }

        public String getText() {
            return text;
        }

        @Override
        public String toString() {
            return this.text;
        }

        public static String[] getTextVlues() {
            String[] values = new String[MATCH_CONDITION.values().length];
            for (int i = 0; i < MATCH_CONDITION.values().length; i++) {
                MATCH_CONDITION con = MATCH_CONDITION.values()[i];
                values[i] = con.getText();
            }
            return values;
        }

        public static int indexOf(String text) {
            for (int i = 0; i < MATCH_CONDITION.values().length; i++) {
                MATCH_CONDITION con = MATCH_CONDITION.values()[i];
                if (con.getText().equals(text)) {
                    return i;
                }
            }
            return -1;
        }
    }
}
