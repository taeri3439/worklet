package com.project2.worklet.component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JobCategoryVO {

    private String primaryCategoryNum;
    private String primaryCategoryName;
    private String secondaryCategoryNum;
    private String secondaryCategoryName;
    private String tertiaryCategoryNum;
    private String tertiaryCategoryName;

}
