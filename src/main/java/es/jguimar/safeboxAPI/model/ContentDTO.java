package es.jguimar.safeboxAPI.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ContentDTO {

    private List<String> items = new ArrayList<String>();

}
