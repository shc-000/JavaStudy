package com.frog.study.algorithm.basis;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 的那 两个 整数，并返回它们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 * <p>
 * 你可以按任意顺序返回答案。
 * 示例 1：
 * <p>
 * 输入：nums = [2,7,11,15], target = 9
 * 输出：[0,1]
 * 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
 *
 * @author haichao.shao (shaohaichao@shanshu.ai)
 * @since 2021/05/17
 */
public class Sum {
    public static void main(String[] args) {
        int[] nums = {2, 7, 11, 15};
        int target = 9;
        int[] result1 = sum(nums, target);
        System.out.println(result1[0] + "-" + result1[1]);
        int[] result2 = twoSum(nums, target);
        System.out.println(result2[0] + "-" + result2[1]);
    }

    /**
     * 常规思维,时间复杂度比较
     */
    static int[] sum(int[] nums, int target) {
        for (int i = 0, len = nums.length; i < len; i++) {
            int v1 = nums[i];
            for (int j = 0; j < len; j++) {
                int v2 = nums[j];
                if (target == (v1 + v2)) {
                    if (i != j) {
                        return new int[]{i, j};
                    }
                }
            }
        }
        return null;
    }

    /**
     * classic,时间复杂度比较
     */
    static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashedMap<>();
        for (int i = 0, len = nums.length; i < len; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            }
            map.put(nums[i], i);
        }
        return new int[]{-1, -1};
    }
}
