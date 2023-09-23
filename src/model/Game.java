package model;

import java.util.*;

public class Game {
    private Integer length;
    private Integer[][] checks;
    private Boolean is_change = true;
    private Integer score=0;
    private Random random;
    private Integer[][] pre_checks;

    public Game(Integer length,long seed) {
        this.length=length;
        checks=new Integer[length][length];
        random=new Random(seed);
        pre_checks=new Integer[length][length];
        initGame();
    }

    public Game(Integer length) {
        this(length,System.currentTimeMillis());
    }
    public Game(Integer[][] checks){
        this(checks.length);
        this.checks=checks;
    }

    public void initGame() {
        score = 0;
        for (int indexRow = 0; indexRow < length; indexRow++) {
            for (int indexCol = 0; indexCol < length; indexCol++) {
                checks[indexRow][indexCol] = 0;
            }
        }
        // 最开始时生成两个数
        is_change = true;
        createCheck();
        is_change = true;
        createCheck();
        for (int indexRow = 0; indexRow < length; indexRow++) {
            for (int indexCol = 0; indexCol < length; indexCol++) {
                pre_checks[indexRow][indexCol] = 0;
            }
        }
    }
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
