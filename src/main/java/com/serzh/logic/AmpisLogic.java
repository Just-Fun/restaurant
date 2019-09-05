package com.serzh.logic;

import java.util.Arrays;
import java.util.Objects;

public class AmpisLogic {

    /**
     * Implement a method ​easy1​ that given an integer array, which each element appears twice except for one.
     * Find that one.
     * <p>
     * Array can contains only positive or negative numbers
     *
     * @param nums - array of integers
     * @return element(int) that appears once
     */
    public int getSingle(int[] nums) {
        if (Objects.isNull(nums) || nums.length < 1) {
            throw new RuntimeException("Array must contains at least one element");
        }

        int result = 0;
        for (int i : nums) {
            result = result ^ i;
        }

        return result;
    }

    public int getSingle2(int[] nums) {
        return Arrays.stream(nums).reduce(0, (a, b) -> a ^ b);
    }

    /**
     * Implement a method ​moderate1​ that given an array of integers ​nums,​
     * returns an array of integers ​output​ where ​output[i]​ = product of all elements in ​nums​ except ​nums[i]
     *
     * @param nums - array of integers
     * @return product of all elements in ​nums​ except ​nums[i]
     */
    public int[] productExceptSelf(int[] nums) {
        if (nums.length == 1) {
            return nums;
        }

        int[] result = new int[nums.length];

        result[nums.length - 1] = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            result[i] = result[i + 1] * nums[i + 1];
        }

        int left = 1;
        for (int i = 0; i < nums.length; i++) {
            result[i] = result[i] * left;
            left = left * nums[i];
        }

        return result;
    }
}
