package br.com.kauebarreto.jsondiff.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Diff {
    @Id
    public Long id;
    @Column
    public String leftContent;
    @Column
    public String rightContent;

    public Diff() {
    }

    public Diff(Long id) {
        this.id = id;
    }

    public Diff(Long id, String leftContent, String rightContent) {
        this.id = id;
        this.leftContent = leftContent;
        this.rightContent = rightContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeftContent() {
        return leftContent;
    }

    public void setLeftContent(String leftContent) {
        this.leftContent = leftContent;
    }

    public String getRightContent() {
        return rightContent;
    }

    public void setRightContent(String rightContent) {
        this.rightContent = rightContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Diff diff = (Diff) o;
        return Objects.equals(id, diff.id) && Objects.equals(leftContent, diff.leftContent) && Objects.equals(rightContent, diff.rightContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, leftContent, rightContent);
    }
}
