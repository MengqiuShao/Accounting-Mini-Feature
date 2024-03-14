import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainMenu {
    //创建一个集合来存储，数组
    static List<Bills> billsList = new ArrayList<>();
    //类加载的时候会第一时间执行
    static{
        billsList.add(new Bills("吃饭支出", 234, "交行", "支出", "2023-3-4", "家庭聚餐"));
        billsList.add(new Bills("购物支出", 456, "招行", "支出", "2023-3-5", "购买衣服"));
        billsList.add(new Bills("工资收入", 789, "建行", "收入", "2023-3-6", "工资发放"));
        billsList.add(new Bills("房租支出", 123, "工行", "支出", "2023-3-7", "月租"));
        billsList.add(new Bills("水电费支出", 567, "农行", "支出", "2023-3-8", "水电费"));
        billsList.add(new Bills("股票收入", 890, "中信", "收入", "2023-3-9", "股票投资收益"));
        billsList.add(new Bills("娱乐支出", 321, "光大", "支出", "2023-3-10", "看电影"));
        billsList.add(new Bills("礼物支出", 654, "民生", "支出", "2023-3-11", "送礼物"));
        billsList.add(new Bills("奖金收入", 987, "邮储", "收入", "2023-3-12", "年终奖金"));
        billsList.add(new Bills("旅游支出", 135, "浦发", "支出", "2023-3-13", "旅游花费"));
        billsList.add(new Bills("兼职收入", 246, "平安", "收入", "2023-3-14", "兼职收入"));
        billsList.add(new Bills("健身支出", 802, "华夏", "支出", "2023-3-16", "健身会员费"));
        billsList.add(new Bills("投资收入", 479, "兴业", "收入", "2023-3-17", "投资收益"));
        billsList.add(new Bills("日常支出", 123, "工行", "支出", "2023-3-18", "日常生活费"));

    }
    public static void main(String[] args) {
        showMain();
        run();

    }
    //展示一级菜单
    public static void showMain() {
        System.out.println("------------随手记----------");
        System.out.println("1. 添加服务 2. 删除服务 3.查询账务 4.退出系统");
        System.out.println("请输入功能序号【1-4】:");

    }
    public static void run(){
        //创建一个flag标志：
        boolean flag = true;
        //创建扫描器
        Scanner scanner = new Scanner(System.in);
        while(flag) {
            //获得输入的选项
            int op = scanner.nextInt();
            //判断1，2，,3，4
            switch (op) {
                case 1:
                    addBills();
                    break;
                case 2:
                    delBills();
                    break;
                case 3:
                    select();
                    break;
                case 4:
                    System.out.println("退出系统");
                    flag = false;
                    break;
                default:
                    System.out.println("请重新输入：");

            }
        }
        System.out.println("退出系统，再见了");
    }

    private static void delBills() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请删除你想要删除项目的ID");
        int id = scanner.nextInt();
        billsList.remove(id - 1);
        System.out.println("删除成功");
        showMain();
    }

    private static void addBills() {
        Scanner scanner = new Scanner(System.in);

        // 向用户提示输入账单详细信息
        System.out.println("请输入账单名称（例如：吃饭支出）：");
        String name = scanner.nextLine();

        System.out.println("请输入金额（例如：234）：");
        double total = scanner.nextDouble();
        scanner.nextLine(); // 消费掉剩余的换行符

        System.out.println("请输入账户（例如：交行）：");
        String account = scanner.nextLine();

        System.out.println("请输入类型（收入/支出）：");
        String type = scanner.nextLine();

        System.out.println("请输入时间（格式：YYYY-MM-DD）：");
        String time = scanner.nextLine();

        System.out.println("请输入描述（例如：家庭聚餐）：");
        String desc = scanner.nextLine();

        // 创建一个新的 Bills 实例并添加到 billsList 中
        Bills newBill = new Bills(name, total, account, type, time, desc);
        billsList.add(newBill);

        System.out.println("账单添加成功！");
        showMain();
    }

    //三种查询 1. 查询所有 2. 按照时间区间来查询 3. 收入和支出的类型查询
    private static void select(){
        System.out.println("随手记>>账务查询");
        System.out.println("1. 查询所有， 2. 按照时间区间查询 3. 收入和支出的类型查询");
        Scanner input = new Scanner(System.in);
        int op3 = input.nextInt();
            switch(op3){
                case 1:
                    selctAll();
                    break;
                case 2:
                    selctByTime();
                    break;
                case 3:
                    selectByType();
                    break;
            }
            //展示上一级主菜单
            showMain();
    }

    private static void selectByType() {
        System.out.println("随手记>>账务查询>>收入和支出类型");
        System.out.println("请输入：收入或支出");
        Scanner scanner = new Scanner(System.in);
        String type = scanner.next();

        // 进行过滤，并将结果收集到新的列表中
        List<Bills> billList = billsList.stream()
                .filter(bills -> {
                    String type1 = bills.getType();
                    return type1.equals(type);
                }).collect(Collectors.toList());

        // 打印过滤后的账单列表
        print(billList);
    }


    private static void selctByTime() {
        //创建一个时间格式化的对象
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("随手记>>账务查询>>请输入开始结束时间");
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：开始时间");
        String start = scanner.next();
        System.out.println("请输入：结束时间");
        String end = scanner.next();
        List<Bills> billList = billsList.stream().filter(bills -> {
            final String time = bills.getTime();
            //字符串解析成具体的时间
            try {
                Date startDate = format.parse(start);
                Date endDate = format.parse(end);
                Date timeDate = format.parse(time);
                if(timeDate.before(endDate) && timeDate.after(startDate)){
                    return true;
                }
            } catch (ParseException e){
                e.printStackTrace();
            }
            return false;
        }).collect(Collectors.toList());
        print(billList);

    }

    private static void selctAll() {
        print(billsList);
    }
    public static void print(List<Bills> bills) {
        // 假设调整后的宽度值
        System.out.println("ID\t类别\t\t\t\t\t\t\t账户\t\t\t\t\t类型\t\t\t\t金额\t\t\t\t时间\t\t\t\t\t\t备注");
        for (int i = 0; i < bills.size(); i++) {
            Bills bill = bills.get(i);
            // 调整格式化字符串中的宽度值以改善对齐
            System.out.printf("%-4d%-25s%-20s%-12s%-16.2f%-24s%s%n",
                    i + 1,
                    bill.getName(),
                    bill.getAccount(),
                    bill.getType(),
                    bill.getTotal(),
                    bill.getTime(),
                    bill.getDesc());
        }
    }
}

