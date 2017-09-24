package com.hengstar.nytimessearch.models;

import com.hengstar.nytimessearch.utils.Constants;

import java.util.Date;
import java.util.HashSet;

public class FilterOptions {
    public enum SortOrder {
        NONE,
        OLDEST {
            public String toString() {
                return Constants.SortParams.OLDEST;
            }
        },
        NEWEST  {
            public String toString() {
                return Constants.SortParams.NEWEST;
            }
        }
    }

    public enum NewsDeskValue {
        ART {
            public String toString(){
                return Constants.NewsDeskValuesParams.ARTS;
            }
        },
        FASHION_STYLE {
            public String toString(){
                return Constants.NewsDeskValuesParams.FASHION_STYLE;
            }
        },
        SPORTS {
            public String toString(){
                return Constants.NewsDeskValuesParams.SPORTS;
            }
        }
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public void setNewsDeskValue(NewsDeskValue newsDeskValue, boolean checked) {
        if (newsDeskValues == null) {
            newsDeskValues = new HashSet<>();
        }

        if (checked) {
            newsDeskValues.add(newsDeskValue);
        } else {
            newsDeskValues.remove(newsDeskValue);
        }
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public HashSet<NewsDeskValue> getNewsDeskValues() {
        return newsDeskValues;
    }

    private Date beginDate;
    private SortOrder sortOrder;
    private HashSet<NewsDeskValue> newsDeskValues;
}
