package cn.gh.fms.controller.random;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 郭宏
 * @date on 2018-12-16.
 */
public class RandomCalculator {

    public static void main(String[] args) {
        float test;
        float sum=0;
        for (int i = 1; i <= 1000; i++) {
            test = getRandomValue(20, 50, 40);
            System.out.println(i + "_____________" + test);
            sum = sum + test;
        }
        System.out.println("avg:----------"+sum/1000);
    }

    public static float getRandomValue(float minPrice, float maxPrice, float maxChancePrice) {
        // TODO: 2018-12-16 判断最大概率点与最大最小值哪个更远
        float middlePoint = (maxPrice + minPrice) / 2;
        float farPoint = middlePoint > maxChancePrice ? maxPrice : minPrice;
        // TODO: 2018-12-16 计算最远点与最大概率点之间范围（长半段）占总范围的百分数
        float longSectionPercent = Math.abs(farPoint - maxChancePrice) / (maxPrice - minPrice) * 100;
        // TODO: 2018-12-16 获得长半段的数值和值和数量，每0.5取一个值
        float longSectionSum = 0;
        float longSectionNum = 0;
        float longSectionMin = farPoint < maxChancePrice ? farPoint : maxChancePrice;
        float longSectionMax = farPoint < maxChancePrice ? maxChancePrice : farPoint;
        float i = longSectionMin;
        while (i <= longSectionMax) {
            longSectionSum = longSectionSum + i;
            longSectionNum++;
            i = i + 0.5f;
        }
        // TODO: 2018-12-16 计算长半段函数 Y=aX+b 中的a、b值
        float longA = longSectionPercent / (longSectionSum - farPoint * longSectionNum);
        float longB = farPoint * longA * -1;

//        System.out.println("a:-----------__" + longA);
//        System.out.println("b:-----------__" + longB);
//        System.out.println("longSectionPercent-----------_" + longSectionPercent);
//
//        float longSectionSumY = 0;
//        float xx = maxChancePrice;
//        while (xx <= maxPrice) {
//            longSectionSumY = longSectionSumY + (longA * xx + longB);
//            xx = xx + 0.5f;
//        }
//        System.out.println(longSectionSumY);


        // TODO: 2018-12-16 计算顶点值的y值，再计算出短半段的b值，短半段的a值为长半段的a值取反
        float topY = longA * maxChancePrice + longB;
        float shortSectionSum = 0;
        float shortSectionNum = 0;
        float shortSectionMin = farPoint < maxChancePrice ? maxChancePrice : minPrice;
        float shortSectionMax = farPoint < maxChancePrice ? maxPrice : maxChancePrice;
        float k = shortSectionMin;
        while (k <= shortSectionMax) {
            if (k != maxChancePrice) {
                shortSectionSum = shortSectionSum + k;
                shortSectionNum++;
            }
            k = k + 0.5f;
        }
        float shortA = ((100 - longSectionPercent) - topY * shortSectionNum) / (shortSectionSum - maxChancePrice * shortSectionNum);
        float shortB = topY - shortA * maxChancePrice;
//        System.out.println("shortA:---------__" + shortA);
//        System.out.println("shortB:---------__" + shortB);
//
//        float shortSectionSumY = 0;
//        float sxx = minPrice;
//        while (sxx < maxChancePrice) {
//            shortSectionSumY = shortSectionSumY + (shortA * sxx + shortB);
//            sxx = sxx + 0.5f;
//        }
//        System.out.println("shortSectionSumY____" + shortSectionSumY);

        List<Float> separates = new ArrayList<>();
        List<Float> percents = new ArrayList<>();
        float j = minPrice;
        float smallScopePercent;
        while (j <= maxPrice) {
            if (Math.abs(farPoint - j) <= Math.abs(maxChancePrice - farPoint)) {
                //j点与远端的距离小于长半段距离，说明j点在长半段
                smallScopePercent = longA * j + longB;
            } else {
                smallScopePercent = shortA * j + shortB;
            }
            if (smallScopePercent != 0) {
                percents.add(smallScopePercent);
            }
            if (j != minPrice && j != maxPrice) {
                separates.add(j);
            }
            j = j + 0.5f;
        }
        return produceRateRandomNumber(minPrice, maxPrice, separates, percents);
    }

    /**
     * 按比率产生随机数
     *
     * @param min       最小值
     * @param max       最大值
     * @param separates 分割值（中间插入数）
     * @param percents  每段数值的占比（几率）
     * @return 按比率随机结果
     */
    public static float produceRateRandomNumber(float min, float max, List<Float> separates, List<Float> percents) {
        if (min > max) {
            throw new IllegalArgumentException("min值必须小于max值");
        }
        if (separates == null || percents == null || separates.size() == 0) {
            return produceRandomNumber(min, max);
        }
        if (separates.size() + 1 != percents.size()) {
            throw new IllegalArgumentException("分割数字的个数加1必须等于百分比个数");
        }
        float totalPercent = 0;
        for (Float p : percents) {
            if (p < 0 || p > 100) {
                throw new IllegalArgumentException("百分比必须在[0,100]之间");
            }
            totalPercent += p;
        }
        if (Math.round(totalPercent) != 100) {
            System.out.println(totalPercent);
            throw new IllegalArgumentException("百分比之和必须为100");
        }
        for (float s : separates) {
            if (s <= min || s >= max) {
                throw new IllegalArgumentException("分割数值必须在(min,max)之间");
            }
        }
        int rangeCount = separates.size() + 1; //例如：3个插值，可以将一个数值范围分割成4段
        //构造分割的n段范围
        List<Range> ranges = new ArrayList<Range>();
        float scopeMax = 0;
        for (int i = 0; i < rangeCount; i++) {
            Range range = new Range();
            range.min = (i == 0 ? min : separates.get(i - 1));
            range.max = (i == rangeCount - 1 ? max : separates.get(i));
            range.percent = percents.get(i);

            //片段占比，转换为[0,100]区间的数字
            range.percentScopeMin = scopeMax;
            range.percentScopeMax = range.percentScopeMin + range.percent;
            scopeMax = range.percentScopeMax;

            ranges.add(range);
        }
        //结果赋初值
        float r = min;
        float randomFloat = (float) Math.random() * 100; //[0,100]
        for (int i = 0; i < ranges.size(); i++) {
            Range range = ranges.get(i);
            //判断使用哪个range产生最终的随机数
            if (range.percentScopeMin <= randomFloat && randomFloat < range.percentScopeMax) {
                r = produceRandomNumber(range.min, range.max);
                break;
            }
        }
        return r;
    }

    /**
     * 产生随机数
     *
     * @param min 最小值
     * @param max 最大值
     * @return 随机结果
     */
    public static float produceRandomNumber(float min, float max) {
        return (float) Math.random() * (max - min) + min; //[min,max]
    }

    public static class Range {
        public float min;
        public float max;
        public float percent; //百分比

        public float percentScopeMin; //百分比转换为[1,100]的数字的最小值
        public float percentScopeMax; //百分比转换为[1,100]的数字的最大值
    }
}
