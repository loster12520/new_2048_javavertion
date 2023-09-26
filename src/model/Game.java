/**
 * 放置在model包内的目的，大概是因为这个也属于一种模型吧
 */
package model;

/**
 * 导包
 */
import java.util.*;

/**
 * Game
 * 本类旨在实现整个游戏的逻辑，所有关于游戏控制以及变更的行为都会在这里实现
 *
 * @author loster12520
 */
public class Game {
    /**
     * 游戏面板边长，一般为4
     */
    private Integer length;
    /**
     * 游戏面板数据保存的数组
     */
    private Integer[][] checks;
    /**
     * 用作移动后要新增新方块时判断是否需要新增
     */
    private Boolean is_change = true;
    /**
     * 分数，虽然现阶段还没有在ui上表现出来，但仍旧提供了访问的接口
     */
    private Integer score=0;
    /**
     * 随机数发生器，用于生成新方块
     */
    private Random random;
    /**
     * 前一步的面板数据数组，用于reset的时候覆写checks
     */
    private Integer[][] pre_checks;

    /**
     * 构造函数的基础实现
     * @param length  同类中的length
     * @param seed 随机数发生器的种子
     */

    public Game(Integer length,long seed) {
        this.length=length;
        //初始化一下面板数据
        checks=new Integer[length][length];
        //初始化随机数发生器
        random=new Random(seed);
        //初始化先前面板
        pre_checks=new Integer[length][length];
        //初始化游戏函数
        initGame();
    }

    /**
     * 构造方法的简易实现
     * @param length 同类中的length
     */
    public Game(Integer length) {
        //省略的seed值直接使用当前系统的时间作为seed值
        this(length,System.currentTimeMillis());
    }

    /**
     * 用于调试的构造方法，可以直接生成对应情况的游戏面板
     * @param checks 游戏面板数据
     */
    public Game(Integer[][] checks){
        this(checks.length);
        this.checks=checks;
    }

    /**
     * 初始化游戏
     */
    public void initGame() {
        //初始化分数
        score = 0;
        //向游戏面板列表的每一项注入0
        for (int indexRow = 0; indexRow < length; indexRow++) {
            for (int indexCol = 0; indexCol < length; indexCol++) {
                checks[indexRow][indexCol] = 0;
            }
        }
        // 最开始时生成两个方块  createCheck是创建新方块的函数
        is_change = true;
        createCheck();
        is_change = true;
        createCheck();
        //向游戏面板列表的副本每一项注入0
        for (int indexRow = 0; indexRow < length; indexRow++) {
            for (int indexCol = 0; indexCol < length; indexCol++) {
                pre_checks[indexRow][indexCol] = 0;
            }
        }
    }

    /**
     * 创建新方块
     */
    private void createCheck() {
        List<Integer[]> list = getEmptyChecks();

        if (!list.isEmpty() && is_change) {
            int index = random.nextInt(list.size());
            Integer i=list.get(index)[0],j = list.get(index)[1];
            // 2, 4出现概率9:1
            int randomValue = random.nextInt(10);
            checks[i][j] = randomValue==0 ? 4 : 2;//只有[0,4)中的2才能生成4
//            is_change = false;
        }
    }

    // 获取空白方格
    private List<Integer[]> getEmptyChecks() {
        List<Integer[]> checkList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (checks[i][j] == 0) {
                    checkList.add(new Integer[]{i,j});
                }
            }
        }
        return checkList;
    }
    //是否全部格子占满，全部占满则GameOver
    private boolean judgeGameOver() {

        if (!getEmptyChecks().isEmpty()) {
            return false;
        }

        for (int i = 0; i < length-1; i++) {
            for (int j = 0; j < length-1; j++) {
                //判断是否存在可合并的方格
                if (checks[i][j] == checks[i][j + 1]
                        || checks[i][j] == checks[i + 1][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public void moveLeft() {
        if(is_change)
            save();
        //找到一个非空格子后checks[i][j].value > 0，可分为三种情况处理
        for (int i = 0; i < length; i++) {
            for (int j = 1, index = 0; j < length; j++) {
                if (checks[i][j] > 0) {
                    //第一种情况：checks[i][j]（非第1列）与checks[i][index]的数相等，则合并乘以2，且得分增加
                    if (checks[i][j].equals(checks[i][index])) {
                        System.out.println("!!!");
                        score += checks[i][index] *= 2;
                        checks[i][j] = 0;
                        is_change = true;
                    } else if (checks[i][index] == 0) {
                        //第二种：若checks[i][index]为空格子，checks[i][j]就直接移到最左边checks[i][index]
                        checks[i][index] = checks[i][j];
                        checks[i][j] = 0;
                        is_change = true;
                    } else if (checks[i][++index] == 0) {
                        //第三种：若checks[i][index]不为空格子，并且数字也不相等，若其旁边为空格子，则移到其旁边
                        checks[i][index] = checks[i][j];
                        checks[i][j] = 0;
                        is_change = true;
                    }
                }
            }
        }
        createCheck();
    }

    public void moveRight() {
        if(is_change)
            save();
        for (int i = 0; i < length; i++) {
            for (int j = length-2, index = length-1; j >= 0; j--) {
                if (checks[i][j] > 0) {
                    if (checks[i][j].equals(checks[i][index])) {
                        score += checks[i][index] *= 2;
                        checks[i][j] = 0;
                        is_change = true;
                    } else if (checks[i][index] == 0) {
                        checks[i][index] = checks[i][j];
                        checks[i][j] = 0;
                        is_change = true;
                    } else if (checks[i][--index] == 0) {
                        checks[i][index] = checks[i][j];
                        checks[i][j] = 0;
                        is_change = true;
                    }
                }
            }
        }
        createCheck();
    }

    public void moveUp() {
        if(is_change)
            save();
        for (int i = 0; i < length; i++) {
            for (int j = 1, index = 0; j < length; j++) {
                if (checks[j][i] > 0) {
                    if (checks[j][i].equals(checks[index][i])) {
                        score += checks[index][i] *= 2;
                        checks[j][i] = 0;
                        is_change = true;
                    } else if (checks[index][i] == 0) {
                        checks[index][i] = checks[j][i];
                        checks[j][i] = 0;
                        is_change = true;
                    } else if (checks[++index][i] == 0){
                        checks[index][i] = checks[j][i];
                        checks[j][i] = 0;
                        is_change = true;
                    }
                }
            }
        }
        createCheck();
    }

    public void moveDown() {
        if(is_change)
            save();
        for (int i = 0; i < length; i++) {
            for (int j = length-2, index = length-1; j >= 0; j--) {
                if (checks[j][i] > 0) {
                    if (checks[j][i].equals(checks[index][i])) {
                        score += checks[index][i] *= 2;
                        checks[j][i] = 0;
                        is_change = true;
                    } else if (checks[index][i] == 0) {
                        checks[index][i] = checks[j][i];
                        checks[j][i] = 0;
                        is_change = true;
                    } else if (checks[--index][i] == 0) {
                        checks[index][i] = checks[j][i];
                        checks[j][i] = 0;
                        is_change = true;
                    }
                }
            }
        }
        createCheck();
    }
    private void save(){
        is_change = false;
        for (int i=0;i<length;i++)
            for (int j=0;j<length;j++)
                pre_checks[i][j]=checks[i][j];
    }
    public void reset(){
        if (pre_checks!=null)
            for (int i=0;i<length;i++)
                for (int j=0;j<length;j++)
                    checks[i][j]=pre_checks[i][j];
        else throw new RuntimeException("no pre checks");
    }

    public void print(){
        System.out.println();
        System.out.println("check:");
        for (Integer[] integers:checks) {
            for (Integer integer:integers)
                System.out.print(integer+" ");
            System.out.println();
        }
        System.out.println();
        System.out.println("pre_check:");
        for (Integer[] integers:pre_checks) {
            for (Integer integer:integers)
                System.out.print(integer+" ");
            System.out.println();
        }
        System.out.println();
        System.out.println("score: "+score);
        System.out.println();
        System.out.println("--------------------");
    }

    public Integer getLength() {
        return length;
    }

    public Integer[][] getChecks() {
        return checks;
    }

    public Integer getScore() {
        return score;
    }

    //only for test
    public static void main(String[] args) {
        Integer[][] chest=new Integer[][]{
                {0,0,0,0},
                {0,0,0,0},
                {0,0,0,0},
                {2,2,4,8}
        };
        Game game=new Game(chest);
        game.print();
        Scanner scanner=new Scanner(System.in);
        String text;
        while ((text=scanner.nextLine())!=null&&!text.isEmpty()){
            switch (text){
                case "a"->game.moveLeft();
                case "w"->game.moveUp();
                case "s"->game.moveDown();
                case "d"->game.moveRight();
            }
            game.print();
        }
    }
}
