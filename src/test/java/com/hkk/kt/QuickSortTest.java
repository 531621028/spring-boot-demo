package com.hkk.kt;

import com.google.common.base.Joiner;
import com.hkk.demo.kt.QuickSort;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author kang
 * @date 2023/10/9
 */
@RunWith(JUnit4.class)
public class QuickSortTest {

    final static QuickSort quickSort = new QuickSort();

    @Test
    public void testCase1() {
        int[] nums = {
            1
        };
        quickSort.sortArray(nums);
        Assert.assertEquals("1", Joiner.on(",").join(Arrays.stream(nums).boxed().collect(Collectors.toList())));
    }


    @Test
    public void testCase2() {
        int[] nums = {
            1, 1
        };
        quickSort.sortArray(nums);
        Assert.assertEquals("1,1", Joiner.on(",").join(Arrays.stream(nums).boxed().collect(Collectors.toList())));
    }

    @Test
    public void testCase3() {
        int[] nums = {
            1, 2, 1
        };
        quickSort.sortArray(nums);
        Assert.assertEquals("1,1,2", Joiner.on(",").join(Arrays.stream(nums).boxed().collect(Collectors.toList())));
    }

    @Test
    public void testCase4() {
        int[] nums = {
            2, 1, 2, 1
        };
        quickSort.sortArray(nums);
        Assert.assertEquals("1,1,2,2",
            Joiner.on(",").join(Arrays.stream(nums).boxed().collect(Collectors.toList())));
    }

    @Test
    public void testCase5() {
        int[] nums = {
            5, 4, 3, 2, 1
        };
        quickSort.sortArray(nums);
        Assert.assertEquals("1,2,3,4,5",
            Joiner.on(",").join(Arrays.stream(nums).boxed().collect(Collectors.toList())));
    }

    @Test
    public void testCase6() {
        int[] nums = {
            -1, 2, -8, -10
        };
        Assert.assertEquals("-10,-8,-1,2",
            Joiner.on(",").join(Arrays.stream(quickSort.sortArray(nums)).boxed().collect(Collectors.toList())));
    }

    @Test
    public void testCase7() {
        int[] nums = {
            -2, 3, -5
        };
        Assert.assertEquals("-5,-2,3",
            Joiner.on(",").join(Arrays.stream(quickSort.sortArray(nums)).boxed().collect(Collectors.toList())));
    }
}
