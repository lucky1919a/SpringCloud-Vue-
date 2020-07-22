package com.course.server.dto;

import com.course.server.common.Page;

public class SectionPageDao extends Page {
    private String courseId;
    private String chapterId;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }
}
