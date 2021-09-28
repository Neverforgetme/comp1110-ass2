package comp1110.ass2.gui;

import comp1110.ass2.Coordinate;
import comp1110.ass2.TwistGame;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * A very simple viewer for 棋子块 placements in the twist game.
 *
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various 棋子块
 * placements.
 * 该课程与您的主要游戏课程分开。 这个class 不玩游戏，它只是说明各种片段
 *   展示位置。
 */
public class Viewer extends Application {

    /* board layout */
    private static final int SQUARE_SIZE = 60;
    private static final int VIEWER_WIDTH = 750;
    private static final int VIEWER_HEIGHT = 500;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();
    TextField textField;
    private Coordinate[] coordinateList = new Coordinate[32];
    //坐标列表

    private BorderPane rootPane = new BorderPane();
    private Image imageChoose = null;// 选择的哪张图片，序号从b0-b3,others全靠的是镜像翻折
    private int Cnum;//钉子或者棋子块 的定位点，以此点作为展开x坐标
    private int Rnum;//钉子或者棋子块 的定位点，以此点作为展开y坐标
    private int CnumT;//钉子或者棋子块 ，展开x坐标，一共占据了几个x空位
    private int RnumT;//钉子或者棋子块 ，展开y坐标，一共占据了几个y空位
    public static HashMap<String,Piece> mapOffset = new HashMap<>();//hash 图
    public static String placementStringNow = "";
    public static String placementStringNowTest = "";
    public static String positionStringNowy = "";
    public String picChosen = null;
    public static ImageView imgViewNow = null;
    public static Stage stageNow = new Stage();
    private static int difficultyStr = 0;
    private static String whichPiece = "";
    private static ArrayDeque<String> dequeLogPosition = new ArrayDeque<>();
    private static int  whichPieceNum = 0;
    private static String[] winStrings = new String[]{"a7A7","b6A7","c1A3","d2A6",
            "e2C3","f3C4","g4A7","h6D0","i6B0","j2B0","j1C0","k3C0","l4B0","l5C0"};
    //the first character identifies which of the eight shapes is being placed (a to h).
    //第一个字符标识要放置八个形状中的哪一个（a 到 h），这么放是唯一的solution which can wins the game


    public String[] pegStrings = new String[]
            {"1A", "2A", "3A", "4A", "5A", "6A", "7A", "8A",
             "1B", "2B", "3B", "4B", "5B", "6B", "7B", "8B",
             "1C", "2C", "3C", "4C", "5C", "6C", "7C", "8C",
             "1D", "2D", "3D", "4D", "5D", "6D", "7D", "8D"};
    //peg钉子（可以扩展为棋盘子总坐标分布）的位置，123456代表x坐标，ABCD代表Y坐标

    /**
     * Draw a placement in the window, removing any previously drawn one在窗口中绘制一个位置，删除任何先前绘制的位置
     * @param placement  A valid placement string
     */
    void makePlacement(String placement) throws IOException {

        // FIXME Task 4: implement the simple placement viewer
        // From Anzee u6744888

        ArrayList<String> listLocationInfo = new ArrayList<>();
        if(placement!=null && (placement.length()%4==0)){

            //get location info of 棋子块 or peg
            listLocationInfo = getLocationFromPlacementStr(placement);

            BorderPane rootPane2 = (BorderPane)FXMLLoader.load(getClass().getResource("IqTwist.fxml"));

            VBox vDownBox = new VBox();
            HBox hboxUp = new HBox();
           HBox hBoxDown = new HBox();
           VBox vbox = new VBox();

          //  if(imageChoose == null)
               // imageChoose = new Image("comp1110/ass2/gui/assets/k.png");
            //if(which棋子块 == null)
              //  which棋子块 = "k";
            //返回了绿色的k钉子


            addPieceOrPegHbox(hboxUp);

            Label label = new Label();
            label.setText(" Option");//选项文字出现
            label.setFont(Font.font("Timer New Roman", FontWeight.BOLD, 20));//文字的字体

            ChoiceBox<Object> cb = new ChoiceBox<>();//单项选择
            cb.setItems(FXCollections.observableArrayList("EASY", "MEDIUM", "DIFFIUCLT"));//三个选项
            String[] greeting = {"EASY", "MEDIUM", "DIFFIUCLT"};//数组储存三个选项
            cb.getSelectionModel().select(difficultyStr);//难度数组
            //private static int difficultyStr = 0
            cb.setTooltip(new Tooltip("Select the difficulty:"));//提示选择难度文字
            cb.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
                difficultyStr = newv.intValue();
                //返回一个此对象在转换为 int 类型后表示的数值。
                //Returns 一个随机数字the value of the specified number as an int
                // 以 int 形式返回指定数字的值，Integer.valueOf() 返回一个整数对象，它相当于一个新的 Integer(Integer.parseInt(s))。
                System.out.println("********** difficultyStr:  " + greeting[newv.intValue()]);//随机指定一个难度
            });
            //private Image imageChoose = null
            //public static ImageView imgViewNow = null


          //下面这写不能运行开始会报错 imgViewNow = new ImageView(imageChoose);
            //                  imgViewNow.setFitHeight(imageChoose.getHeight()/4 );//4个为一个
            //                  imgViewNow.setFitWidth(imageChoose.getWidth()/4);
            //                picAddMonitor(imgViewNow,"*" + which棋子块);

                    vbox.getChildren().addAll(cb,label,imgViewNow);

            //draw init Placement绘制初始化放置
            //make Placement进行安置
            //以前的定义ArrayList<String> listLocationInfo = new ArrayList<String>()

            GridPane pane = addGridPane(listLocationInfo);

            hBoxDown.getChildren().addAll(pane,vbox);
            vDownBox.getChildren().addAll(hboxUp,hBoxDown);
            makeControls();


            Stage anotherStage = stageNow;
            anotherStage.setScene(new Scene(rootPane2, 1133, 800));//代表的是总stage长度宽度
            anotherStage.setTitle("TWIST GAME VIEWER");//标题
            rootPane2.setCenter(vDownBox);//放下的都是

            if(placement.length() == 56)
                setAlertContent("WIN !");
            //private static String[] winStrings = new String[]{"a7A7","b6A7","c1A3","d2A6",
         //   "e2C3","f3C4","g4A7","h6D0","i6B0","j2B0","j1C0","k3C0","l4B0","l5C0"};
        //the first character identifies which of the eight shapes is being placed (a to h).
        //第一个字符标识要放置八个形状中的哪一个（a 到 h），这么放是唯一的solution which can wins the game

            anotherStage.show();//展示画板


        }

    }

    /**
     * Randomly generate a valid start string
     * and Randomly place two 棋子块
     */
    public String startPlacements() {
        //Randomly place two 棋子块随机放两个棋子块和选择难度对应的钉子
        //From Anzee u6744888
        char[] pieceOrPeg = {'a','b','c','d','e','f','g','h'};//棋子块和peg共享的颜色
        char[] rowRandom = {'A','B','C','D'};//共享的行为4个字幕 column代笔列是8个数字

        String initPlacements = "";

        int rightLength =0;
        switch (difficultyStr){
            case 0:
                initPlacements = "j2B0j1C0k3C0l4B0l5C0";
                rightLength = 20;
                //j2B0
                // j1C0
                // k3C0
                // l4B0
                // l5C0 给了5个钉子难度0党
                break;
            case 1:
                initPlacements = "j2B0k3C0l5C0";
                rightLength = 12;//
                // j2B0
                // k3C0
                // l5C0
                // 总长度12 给了3个钉子，难度1挡
                break;
            case 2:
                initPlacements = "";
                //暂时不做
        }

        String justLog = initPlacements;
        while(initPlacements.length()<=rightLength || (initPlacements=="")
                || (!TwistGame.isPlacementStringWellFormed(initPlacements))
                ||(!TwistGame.isPlacementStringValid(initPlacements)))
        {
            initPlacements = justLog;
            Random random=new Random();
            for(int i=0; i<1 ;i++){

                String whichPiece = "";
                while((!TwistGame.isPlacementWellFormed(whichPiece)) && (!TwistGame.isPlacementStringValid(whichPiece)))
                {
                    whichPiece = "";
                    int c1 = random.nextInt(13);
                    //随机生成了12个数字 也就是3个
                    whichPiece = winStrings[c1];
                    System.out.println(whichPiece);
                }
                initPlacements = BubbleSort(getLocationFromPlacementStr(initPlacements + whichPiece));
            }
        }

        System.out.println("***************************************************************");
        System.out.println("********** 随机生成的状态字符串  : " + initPlacements);

        if((initPlacements==null) && (initPlacements == "")){
            return null;
        }else {
            return initPlacements;//生成了两个棋子块和难度对应的钉子
        }

    }

    /**
     * Randomly generate a valid start string开始符合要求的placament string
     */
    public String startOldPlacements() {
        //Randomly place two 棋子块
        //From Anzee u6744888
        char[] pieceOrPeg = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};//用于随机生成字幕
        char[] rowRandom = {'A', 'B', 'C', 'D'};

        String initPlacements = "";

        switch (difficultyStr) {
            case 0:
                initPlacements = "j2B0j1C0k3C0l4B0l5C0";
                //j2B0
                // j1C0
                // k3C0
                // l4B0
                // l5C0 给了5个钉子难度0挡
                break;
            case 1:
                initPlacements = "j2B0k3C0l5C0";
                // j2B0// k3C0// l5C0
                // 总长度12 给了3个钉子，难度1挡

                break;
            case 2:
                initPlacements = "";
                //难度2挡暂时不做
        }

        String justLog = initPlacements;//justlog就是初始字符串
        while ((initPlacements == "") || (!TwistGame.isPlacementStringWellFormed(initPlacements)) || (!TwistGame.isPlacementStringValid(initPlacements))) {
            initPlacements = justLog;//justlog就是初始字符串
            Random random = new Random();//创建一个新的随机数生成器
            for (int i = 0; i < 1; i++) {

                String whichPiece = "";
                while ((!TwistGame.isPlacementWellFormed(whichPiece)) && (!TwistGame.isPlacementStringValid(whichPiece))) {
                    whichPiece = "";
                    int c1 = random.nextInt(8);//第一个x坐标以及后面【0-7）随机生成一个
                    int c2 = random.nextInt(8) + 1;////第二个x坐标以及后面【0-8）随机生成一个
                    int c3 = random.nextInt(4);//第三个x坐标以及后面【0-3）随机生成一个
                    int c4 = random.nextInt(8);//第四个x坐标以及后面【0-7）随机生成一个
                    whichPiece = whichPiece + pieceOrPeg[c1] + c2 + rowRandom[c3] + c4;
                    //空字符串/原来就有的钉子+第一个颜色字母+列坐标+行字母坐标+旋转数字
                    // char[] 棋子块OrPeg = {'a','b','c','d','e','f','g','h'};//棋子块和peg共享的用于随机生成颜色字母
                    //  char[] rowRandom = {'A','B','C','D'};//棋子块和peg共享的行4个字母 column代笔列是8个数字
                    System.out.println(whichPiece);
                }
                initPlacements = BubbleSort(getLocationFromPlacementStr(initPlacements + whichPiece));//冒泡排序找到
            }
        }

        System.out.println("***************************************************************");
        System.out.println("********** 随机生成的状态字符串  : " + initPlacements);

        if ((initPlacements == null) && (initPlacements == "")) {
            return null;
        } else {
            return initPlacements;
        }

    }

//    Display images of 棋子块 in the window (anywhere) Translate peg positions to x and y positions in the window. Display images of 棋子块 so that their origin is in the correct place.
//    在窗口中显示工件的图像（任意位置）将钉子的位置转换为窗口中的x列和y行位置。显示工件的图像，使其原点位于正确的位置。
//    Display images so that their origin is in the correct place and their orientation is correct.
//显示图像，使其原点位于正确位置且方向正确。
//    Break placement strings into 棋子块 placements.   Anzee
    //将总的放置字符串分解为棋子块放置字符串// FIXME Task 8 and copy it
    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField ();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    //stageNow.close();
                    makePlacement(textField.getText());

                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                textField.clear();
            }
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    //Basic linear search function基本线性搜索功能
    public Coordinate getPegCoordinates(String pegID) {
        for (int i = 0; i < coordinateList.length - 1; i++) {
            if (coordinateList[i].pegID == pegID) {
                return coordinateList[i];
            }
        }
        //If the last element in the list is reached and the peg hasn't been found, it must be the last element.
        //如果到达列表中的最后一个元素并且未找到钉子，则它必须是最后一个元素
        return coordinateList[coordinateList.length - 1];
    }


    @Override
    public void start(Stage primaryStage) throws Exception {


        rootPane = (BorderPane)FXMLLoader.load(getClass().getResource("IqTwist.fxml"));
        stageNow = primaryStage;
        primaryStage.setTitle("A SIMPLE GAME VIEWER");//左上角的小标题
        VBox vDownBox = new VBox();
        HBox hboxUp = new HBox();
        HBox hBoxDown = new HBox();
        VBox vbox = new VBox();
        //选中的棋子块 or peg
        imageChoose = new Image("comp1110/ass2/gui/assets/k.png");
        //private Image imageChoose = null //BorderPane 在顶部、左侧、右侧、底部和中心位置布置子项。
        //顶部和底部的孩子将被调整到他们喜欢的高度并扩展边框窗格的宽度。 左右子节点将被调整为它们的首选宽度，
        // 并在顶部和底部节点之间扩展长度。
        // 并且中心节点将被调整大小以填充中间的可用空间。 任何位置都可以为空。
       // ImageView imageViewChoosen = new ImageView();
        addPieceOrPegHbox(hboxUp);
        //获得一个下拉框 选择不同的难度
        Label label = new Label();
        label.setText(" Options of the difficulties");//label文字
        label.setFont(Font.font("Timer New Roman", FontWeight.BLACK, 10));//label字体
        ChoiceBox<Object> DifficultyChoiceBox = new ChoiceBox<>();//选择box
        DifficultyChoiceBox.setItems(FXCollections.observableArrayList("Easy", "Medium", "Hard"));
        String[] greeting = {"Easy", "Medium", "Hard"};//字符串数组
        DifficultyChoiceBox.getSelectionModel().select(0);
        //由于 SingleSelectionModel 只能支持一次选择一个索引，这也会导致任何先前选择的索引都被取消选择。
        DifficultyChoiceBox.setTooltip(new Tooltip("Select the difficulty:"));//提示文字
        DifficultyChoiceBox.getSelectionModel().selectedIndexProperty().addListener((ov,oldv,newv)->{
            difficultyStr = newv.intValue();
            //private static int difficultyStr = 0
            System.out.println("********** difficultyStr:  " + greeting[newv.intValue()]);
        });

        imgViewNow = new ImageView(imageChoose);
        vbox.getChildren().addAll(DifficultyChoiceBox,label,imgViewNow);
        /***********************************************************************************************/
        //绘制初始化放置程序
        GridPane rootDown = addGridPane();
        //GridPane 在行和列的灵活网格中布置其子项。 如果设置了边框和/或内边距，则其内容将布置在这些插图中。
        //子项可以放置在网格内的任何位置，并且可以跨越多个行/列。
        hBoxDown.getChildren().addAll(rootDown,vbox);
        vDownBox.getChildren().addAll(hboxUp,hBoxDown);
        makeControls();///创建用于输入的基本文本字段和刷新按钮。
        setOffset();
        rootPane.setCenter(vDownBox);
        primaryStage.setScene(new Scene(rootPane, 933, 700));
        primaryStage.show();
    }

    /**
     * Create blank board
     */
    private GridPane addGridPane(){
        GridPane grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 100, 10));

        for (int column=0; column<8; column++){
            grid.getColumnConstraints().add(new ColumnConstraints(100));
        }
        for (int row=0; row<4; row++){
            grid.getRowConstraints().add(new RowConstraints(100));
        }
        for(int column=0; column<8; column++ ) {
            for(int row=0; row<4; row++ ){
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(100);
                rectangle.setHeight(100);
                rectangle.setFill(Color.ANTIQUEWHITE);
                grid.add(rectangle, column, row);
            }
        }

        return grid;
    }

    /**
     * Create board according to list ,which contain the location information of every 棋子块 or peg
     * 根据列表创建棋盘，其中包含每个棋子或钉子的位置信息
     */
    private GridPane addGridPane(ArrayList<String> list){

        GridPane grid = new GridPane();
        grid.setHgap(1);
        grid.setVgap(1);
        grid.setGridLinesVisible(true);
        grid.setPadding(new Insets(10, 10, 100, 10));

        //draw blank board
        for (int column=0; column<8; column++){
            grid.getColumnConstraints().add(new ColumnConstraints(100));
        }
        for (int row=0; row<4; row++){
            grid.getRowConstraints().add(new RowConstraints(100));
        }
        for(int column=0; column<8; column++ ) {
            for(int row=0; row<4; row++ ){
                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(100);
                rectangle.setHeight(100);
                rectangle.setFill(Color.ANTIQUEWHITE);
                grid.add(rectangle, column, row);
            }
        }

        //add 棋子块 or peg to board
        for (String str: list) {
            System.out.println("***************************************************************");
            System.out.println("********** " + str + "在GridPane中坐标以及偏移量：");
            String picPath = getPicLocationFromStr(str);
            //picPath = "lztest1016/assets/pic/a/a7.png";
            //System.out.println(picPath);
            Image image = new Image(picPath);
            String[] sss = str.split("/");
            char[] chars = sss[sss.length-1].toCharArray();
            char c = chars[0];

            if('a'<=c && c<='l'){
                    System.out.println("Cnum " + Cnum);
                    System.out.println("Rnum  "+ Rnum);
                    System.out.println("CnumT  " + CnumT);
                    System.out.println("RnumT  "  + RnumT  );
                    grid.add(new ImageView(image),Cnum,Rnum-1,CnumT,RnumT);
            }
        }
            return grid;
    }

    /*
    * get the path of pic , according to string , eg."a7A7"
    * */
    private String getPicLocationFromStr(String str){
        String picLoca = null;
        char[] chars = str.toCharArray();
        char which = chars[0];
        int x = Integer.parseInt(String.valueOf(chars[1]));
        char y = chars[2];
        char whichAdd = chars[3];

        String picName = String.valueOf(chars[0]) + String.valueOf(chars[3]);
        //System.out.println("======" + picName);

        if('a'<=which && which<='h'){
            RnumT = mapOffset.get(picName).getHeightInGridPane();
            CnumT = mapOffset.get(picName).getWidthInGridPane();
        }else {
            CnumT = 1;
            RnumT = 1;
        }
        Cnum = x - 1;
        Rnum = y - 'A' + 1;

        picLoca = "comp1110/ass2/gui/assets/pic/" + which + "/" + which +  whichAdd +".png";
        return picLoca;
    }

    /*
     * get the list of every pic status string , according to placement string
     * */
    public static ArrayList<String> getLocationFromPlacementStr(String PlacementStr){
        ArrayList<String> locationInfo = new ArrayList<>();
        char[] chars = PlacementStr.toCharArray();
        String str = "";
        int i = 0;
        for (char c : chars ) {
            i++;
            str = str + c;
            if( i==4 ) {
                locationInfo.add(str);
                i = 0;
                str = "";
            }
        }
        return locationInfo;
    }

    @FXML
    protected void handleButtonAction(Event event) throws IOException {

        String idBt = ((Button)event.getSource()).getId();

        if ( ("btStart").equals(idBt) ){
            String strT = startPlacements();
            placementStringNow = strT;
            placementStringNowTest = placementStringNow;
            imageChoose = null;
            whichPiece = null;
            makePlacement(placementStringNow);
            //makePlacement("a7A7b6A7c1A3d2A6e2C3f3C4g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0");
            //makePlacement("c3A3a6B7");
        }

        if ( ("btBack").equals(idBt) ){

            if(!dequeLogPosition.isEmpty()){
                System.out.println("***************************************************************");
                System.out.println("********** 撤销操作 撤销一步：");
                System.out.println("********** 撤销前placementStringNow： ");
                System.out.println(dequeLogPosition.size());
                placementStringNow = placementStringNow.replaceAll(dequeLogPosition.pop(),"");
                placementStringNowTest = placementStringNow;
                makePlacement(placementStringNow);
                System.out.println("********** 撤销后placementStringNow： ");
                //makePlacement("a7A7b6A7c1A3d2A6e2C3f3C4g4A7h6D0i6B0j2B0j1C0k3C0l4B0l5C0");
                //makePlacement("c3A3a6B7");
            }
        }

        if ( ("btRefresh").equals(idBt) ){

            System.out.println("***************************************************************");
            System.out.println("被测试的字符串是： " + placementStringNowTest);
            System.out.println("状态字符串是： " + placementStringNow);
            System.out.println( "look !!!!!!!!!!!!!!!!!  测试字符串格式是否符合要求 ");
            System.out.println("测试结果是：" + TwistGame.isPlacementStringWellFormed(placementStringNowTest));
            System.out.println( "look !!!!!!!!!!!!!!!!!  测试字符串格式是否有效 ");
            System.out.println("测试结果是：" + TwistGame.isPlacementStringValid(placementStringNowTest));
            System.out.println("***************************************************************");
            placementStringNowTest = placementStringNow;
        }

        if("btQuit".equals(idBt)){
            System.exit(0);
        }

        if(whichPiece!=null && whichPiece!="" &&
                'a'<=whichPiece.charAt(0) && whichPiece.charAt(0)<='h'){

            if(("btClockwise").equals(idBt)){
                rotateAndFlip(1);
            }
            if(("btCounterclockwise".equals(idBt))){
                rotateAndFlip(2);
            }
            if(("btLeftRight".equals(idBt))){
                rotateAndFlip(3);
            }
            if("btUpDown".equals(idBt)){
                rotateAndFlip(4);
            }

        }


    }

    /*
     * Monitor picture drag and drop
     * 这里需要完成拖拉效果，做的不好需要大改动
     * */
    private void picAddMonitor(ImageView imageV,String whichPieceOrPeg){

            imageV.setOnMousePressed(event -> {      // mouse press indicates begin of drag按下开始拖拉

                if(whichPieceOrPeg.length() == 2 && !(String.valueOf(whichPieceOrPeg.charAt(1)).equals(whichPiece))){
                    whichPieceNum = 0;
                }else
                    if(whichPieceOrPeg.length() == 1 && whichPieceOrPeg!=whichPiece) {
                    whichPieceNum = 0;
                }
                System.out.println("setOnMousePressed  "  + whichPieceOrPeg);//鼠标点中了某一个对象
               // imageChoose = new Image();
                String c = null;
                if (whichPieceOrPeg != "")
                    if (whichPieceOrPeg != null) {
                        if (whichPieceOrPeg.length() == 2)
                        {
                            c = String.valueOf(whichPieceOrPeg.charAt(1));
                            whichPiece = String.valueOf(whichPieceOrPeg.charAt(1));
                        }
                        else {
                            c = String.valueOf(whichPieceOrPeg.charAt(0));
                            whichPiece = whichPieceOrPeg;
                        }
                        imageChoose = new Image("comp1110/ass2/gui/assets/pic/" + whichPiece + "/" + whichPiece + whichPieceNum + ".png");
                       //选择并且打印出我需要的assets/a组/a0.png
                        System.out.println("imageChoose  " + "comp1110/ass2/gui/assets/pic/"
                                + whichPiece + "/" + whichPiece + whichPieceNum + ".png");

                        imgViewNow = new ImageView(imageChoose);

                        //  if (whichPieceOrPeg.length() != 2) {
                        //    try {
                        //        makePlacement(placementStringNow);
                        //     }
                        //     catch (IOException e) {
                        //        e.printStackTrace();
                        //     }
                        // }

                    }

                //System.out.println("event.getSceneX() " + event.getSceneX());
               // System.out.println("event.getSceneY() " + event.getSceneY());
            });
            //做的很不好，没有动画运动的效果，而且也没有拖动成功的感觉，需要改进
        imageV.setOnMouseDragged(event -> {      // mouse is being dragged

            System.out.println("setOnMousePressed" );
            double movementX = event.getSceneX();
            double movementY = event.getSceneY();
            System.out.println("event.getSceneX() " + event.getSceneX());
            System.out.println("event.getSceneY() " + event.getSceneY());
            event.consume();
        });


        imageV.setOnMouseReleased(event -> {     // drag is complete
            {
                System.out.println("***************************************************************");
                System.out.println("event.getSceneX() ===== " + event.getSceneX());
                System.out.println("event.getSceneY() ===== " + event.getSceneY());

                List<Integer> listForPosi = new ArrayList<>();//整数数组
                CalculatePositonForPlacementString(listForPosi,event.getSceneX(),event.getSceneY());
                //private void CalculatePositonForPlacementString(List<Integer> position,
                //                                                Double X,
                //                                                Double Y)

                if (listForPosi!=null && listForPosi.size()==2 ){
                    String strPosition = null;
                    String str01 = whichPiece;

                    String str02 =  listForPosi.get(0).toString();;
                    String str03 = "" + ((char) ((listForPosi.get(1).intValue() - 1) + 'A' ));
                    String str04 = String.valueOf(whichPieceNum);
                    strPosition = str01 + str02 + str03 + str04;

                    String strTest = "";
                    strTest = placementStringNow + strPosition;
                    strTest = BubbleSort(getLocationFromPlacementStr(strTest));
                    if(strPosition!=null && TwistGame.isPlacementWellFormed(strPosition) &&
                            TwistGame.isPlacementStringValid(strPosition) && TwistGame.isPlacementWellFormed(strPosition)){
                        System.out.println("********** position no question");
                        System.out.println("********** strPosition: " + strPosition);
                        if ( strTest =="" ||
                                (TwistGame.isPlacementStringWellFormed(strTest) && TwistGame.isPlacementStringValid(strTest))){

                            System.out.println("********** 托放上一个片之前的状态字符串placementStringNow：  " + placementStringNow);
                            placementStringNow = strTest;
                            System.out.println("********** strPosition: " + strPosition);
                            dequeLogPosition.push(strPosition);
                            System.out.println("********** strPosition: " + dequeLogPosition.size());
                            System.out.println("=========== CHANGE");
                            placementStringNowTest = placementStringNow;
                        }
                    }
                    placementStringNowTest = strTest;
                    System.out.println("********** 托放上一个片之后的状态字符串placementStringNow：  " +  placementStringNow);
                    try {
                        //stageNow.close();
                        makePlacement(placementStringNow);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    /*
     * Calculate the coordinates in the GridPane based on the coordinates根据坐标计算GridPane中的坐标
     * */
    private void CalculatePositonForPlacementString(List<Integer> position,Double X, Double Y) {

        if(10<X && X<810 && 208<Y && Y<608){

            System.out.println("***************************************************************");
            System.out.println(" =============在GridPane中的坐标");

            int x1;
            if(0< X && X<100){
                x1 = 1;
                position.add(x1);
                System.out.println("x1 = " + x1);
            }else if(710<X && X<810){
                x1 = 8;
                position.add(x1);
                System.out.println("x1 = " + x1);
            }else{
                x1 = (X.intValue() - 10) / 100 + 1;
                position.add(x1);
                System.out.println("x1 = " + x1);
            }

            int y1;
            if(208< Y && Y<308){
                y1 = 1;
                position.add(y1);
                System.out.println("y1 = " + y1);
            }else if(508< Y && Y<608){
                y1 = 4;
                position.add(y1);
                System.out.println("y1 = " + y1);
            }else {
                y1 = (Y.intValue() - 208) / 100 + 1;
                position.add(y1);
                System.out.println("y1 = " + y1);
            }
            System.out.println("***************************************************************");
        }
    }

    /*
     * Sort the status string using bubble sort a-l
     * 使用冒泡排序 a-l 对状态字符串进行排序
     * */
    public static String BubbleSort(ArrayList<String> strs) {
        String[] strList = strs.toArray(new String[0]);
        String temp = "";
        int size = strList.length;

        for(int i = 0 ; i < size-1; i ++)
        {
            for(int j = 0 ;j < size-1-i ; j++)
            {//冒泡排序
                if(strList[j].charAt(0) > strList[j+1].charAt(0))
                {
                    temp = strList[j];
                    strList[j] = strList[j+1];
                    strList[j+1] = temp;//利用冒泡交换数值小的在前，大的灾后
                }
            }
        }

        String strss = "";
        for (String s: strList) {
            strss += s;
        }
//整个字符串strlist直接拼接再一起。可以简化为return strList
        return strss;
    }

    /*
     * make map that stores the image name and offset
     * */
    private void setOffset(){

        mapOffset.put("a0",new Piece("a0",2,3));
        mapOffset.put("a1",new Piece("a1",3,2));
        mapOffset.put("a2",new Piece("a2",2,3));
        mapOffset.put("a3",new Piece("a3",3,2));
        mapOffset.put("a4",new Piece("a4",2,3));
        mapOffset.put("a5",new Piece("a5",3,2));
        mapOffset.put("a6",new Piece("a6",2,3));
        mapOffset.put("a7",new Piece("a7",3,2));

        mapOffset.put("b0",new Piece("b0",2,3));
        mapOffset.put("b1",new Piece("b1",3,2));
        mapOffset.put("b2",new Piece("b2",2,3));
        mapOffset.put("b3",new Piece("b3",3,2));
        mapOffset.put("b4",new Piece("b4",2,3));
        mapOffset.put("b5",new Piece("b5",3,2));
        mapOffset.put("b6",new Piece("b6",2,3));
        mapOffset.put("b7",new Piece("b7",3,2));

        mapOffset.put("c0",new Piece("c0",1,4));
        mapOffset.put("c1",new Piece("c1",4,1));
        mapOffset.put("c2",new Piece("c2",1,4));
        mapOffset.put("c3",new Piece("c3",4,1));
        mapOffset.put("c4",new Piece("c4",1,4));
        mapOffset.put("c5",new Piece("c5",4,1));
        mapOffset.put("c6",new Piece("c6",1,4));
        mapOffset.put("c7",new Piece("c7",4,1));

        mapOffset.put("d0",new Piece("d0",2,3));
        mapOffset.put("d1",new Piece("d1",3,2));
        mapOffset.put("d2",new Piece("d2",2,3));
        mapOffset.put("d3",new Piece("d3",3,2));
        mapOffset.put("d4",new Piece("d4",2,3));
        mapOffset.put("d5",new Piece("d5",3,2));
        mapOffset.put("d6",new Piece("d6",2,3));
        mapOffset.put("d7",new Piece("d7",3,2));

        mapOffset.put("e0",new Piece("e0",2,2));
        mapOffset.put("e1",new Piece("e1",2,2));
        mapOffset.put("e2",new Piece("e2",2,2));
        mapOffset.put("e3",new Piece("e3",2,2));
        mapOffset.put("e4",new Piece("e4",2,2));
        mapOffset.put("e5",new Piece("e5",2,2));
        mapOffset.put("e6",new Piece("e6",2,2));
        mapOffset.put("e7",new Piece("e7",2,2));

        mapOffset.put("f0",new Piece("f0",2,3));
        mapOffset.put("f1",new Piece("f1",3,2));
        mapOffset.put("f2",new Piece("f2",2,3));
        mapOffset.put("f3",new Piece("f3",3,2));
        mapOffset.put("f4",new Piece("f4",2,3));
        mapOffset.put("f5",new Piece("f5",3,2));
        mapOffset.put("f6",new Piece("f6",2,3));
        mapOffset.put("f7",new Piece("f7",3,2));

        mapOffset.put("g0",new Piece("g0",3,3));
        mapOffset.put("g1",new Piece("g1",3,3));
        mapOffset.put("g2",new Piece("g2",3,3));
        mapOffset.put("g3",new Piece("g3",3,3));
        mapOffset.put("g4",new Piece("g4",3,3));
        mapOffset.put("g5",new Piece("g5",3,3));
        mapOffset.put("g6",new Piece("g6",3,3));
        mapOffset.put("g7",new Piece("g7",3,3));

        mapOffset.put("h0",new Piece("h0",1,3));
        mapOffset.put("h1",new Piece("h1",3,1));
        mapOffset.put("h2",new Piece("h2",1,3));
        mapOffset.put("h3",new Piece("h3",3,1));
        mapOffset.put("h4",new Piece("h4",1,3));
        mapOffset.put("h5",new Piece("h5",3,1));
        mapOffset.put("h6",new Piece("h6",1,3));
        mapOffset.put("h7",new Piece("h7",3,1));
    }

    /*
     * Add an instance image to hbox
     * 将实例图像添加到 hbox
     * 横着的第一条
     * */
    private void addPieceOrPegHbox(HBox hBox){
        Label choosePieceOrPeg = new Label("   CHOOSE: ");
        choosePieceOrPeg.setFont(Font.font("Timer New Roman", 30));
        Image img1 = new Image("comp1110/ass2/gui/assets/a.png");
                ImageView imgv1 = new ImageView(img1);
                imgv1.setFitHeight(50);
                imgv1.setFitWidth(75);
               picAddMonitor(imgv1,"a");//不知道这个是干吗用的
        //红色横着的a
               Image img2 = new Image("comp1110/ass2/gui/assets/b.png");
                ImageView imgv2 = new ImageView(img2);
                imgv2.setFitHeight(50);
               imgv2.setFitWidth(75);
             picAddMonitor(imgv2,"b");
//红色横着的b
        Image img3 = new Image("comp1110/ass2/gui/assets/c.png");
        ImageView imgv3 = new ImageView(img3);
        imgv3.setFitHeight(25);
        imgv3.setFitWidth(100);
        picAddMonitor(imgv3,"c");
        Image img4 = new Image("comp1110/ass2/gui/assets/d.png");
        ImageView imgv4 = new ImageView(img4);
        imgv4.setFitHeight(50);
        imgv4.setFitWidth(75);
        picAddMonitor(imgv4,"d");
        Image img5 = new Image("comp1110/ass2/gui/assets/e.png");
        ImageView imgv5 = new ImageView(img5);
        imgv5.setFitHeight(50);
        imgv5.setFitWidth(50);
        picAddMonitor(imgv5,"e");
        Image img6 = new Image("comp1110/ass2/gui/assets/f.png");
        ImageView imgv6 = new ImageView(img6);
        imgv6.setFitHeight(50);
        imgv6.setFitWidth(75);
        picAddMonitor(imgv6,"f");
        Image img7 = new Image("comp1110/ass2/gui/assets/g.png");
        ImageView imgv7 = new ImageView(img7);
        imgv7.setFitHeight(50);
        imgv7.setFitWidth(75);
        picAddMonitor(imgv7,"g");
        Image img8 = new Image("comp1110/ass2/gui/assets/h.png");
        ImageView imgv8 = new ImageView(img8);
        imgv8.setFitHeight(25);
        imgv8.setFitWidth(75);
        picAddMonitor(imgv8,"h");
        Image img9 = new Image("comp1110/ass2/gui/assets/i.png");
        ImageView imgv9 = new ImageView(img9);
        imgv9.setFitHeight(50);
        imgv9.setFitWidth(50);
        picAddMonitor(imgv9,"i");
        Image img10 = new Image("comp1110/ass2/gui/assets/j.png");
        ImageView imgv10 = new ImageView(img10);
        imgv10.setFitHeight(50);
        imgv10.setFitWidth(50);
        picAddMonitor(imgv10,"j");
        Image img11 = new Image("comp1110/ass2/gui/assets/k.png");
        ImageView imgv11 = new ImageView(img11);
        imgv11.setFitHeight(50);
        imgv11.setFitWidth(50);
        picAddMonitor(imgv11,"k");
        Image img12 = new Image("comp1110/ass2/gui/assets/l.png");
        ImageView imgv12 = new ImageView(img12);
        imgv12.setFitHeight(50);
        imgv12.setFitWidth(50);
        picAddMonitor(imgv12,"l");

        hBox.getChildren().addAll(choosePieceOrPeg,//imgv1,imgv2,
                imgv3,imgv4,
                imgv5,imgv6,imgv7,imgv8,imgv9,imgv10,imgv11,imgv12);


    }
//展示旋转和翻折逻辑的图片
    private void rotateAndFlip(int actionType){
//字母后面的0-3代表着原本的形状，数字0代表了3点钟，1代表了6点钟，2代表了9点钟，3代表了12点钟
        //字母后面的4-7代表着反着的形状，数字4代表了3点钟，5代表了6点钟，6代表了9点钟，7代表了12点钟
        System.out.println("***************************************************************");
        System.out.println("旋转前的setOn Clockwise时针方向 "  + whichPiece + whichPieceNum);
        //imageChoose = new Image();
        String c = null;
        if(whichPiece!=null && whichPiece!="") {
            actionRotateAndFlip(actionType);
            String whichPieceName = whichPiece + whichPieceNum;
            System.out.println("旋转后的时针方向：" + whichPieceName);
            imageChoose = new Image("comp1110/ass2/gui/assets/pic/" + whichPiece + "/" + whichPieceName + ".png");
            System.out.println("imageChoose  " + "comp1110/ass2/gui/assets/pic/" + whichPiece + "/" + whichPieceName + ".png");

            imgViewNow = new ImageView(imageChoose);
            try {
                makePlacement(placementStringNow);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    /*
     *   setAlertContent设置警报内容
     * */
    private void setAlertContent(String alertContent)
    {Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(alertContent);
        alert.showAndWait();
    }
    /*
     *  get the result after Rotate or Flip
     *  1 clockwise rotation
     *  2 Anticlockwise rotation

     *  3 horizontal flip
    * 4 Vertical * */

    private void actionRotateAndFlip(int ActionType){
        //字母后面的0-3代表着原本的形状，数字0代表了3点钟，1代表了6点钟，2代表了9点钟，3代表了12点钟
        //数字0代表着右，1代表着下，2代表随着左，3代表着上
        //字母后面的4-7代表着反着的形状，数字4代表了3点钟，5代表了6点钟，6代表了9点钟，7代表了12点钟
        //数字4代表着右，5代表着下，6代表随着左，7代表着上
        int resultPic = 0;
        //private static int which棋子块Num = 0
        int num = whichPieceNum + 1;

        switch (ActionType){
            case 1:
                //顺时针旋转
                if(1<=num && num<=3)
                {
                    num++;
                }else
                    if(num == 4){
                    num =1;
                              }else
                               if(5<=num && num<=7){
                                  num++;}else if(num==8){
                                      num=4;}
                whichPieceNum = (--num);
                break;
            case 2:
                //逆时针旋转
                if(2<=num && num<=4){
                    num--;
                }else if(num == 1){
                    num =4;
                }else if(6<=num && num<=8){
                    num--;
                }else if(num==5){
                    num=8;
                }
                whichPieceNum = (--num);
                break;
            case 3:
                //水平翻转
                if(num==4 || num==8){
                    num = 12 - num;
                }else if(1<=num && num<=7){
                    num = 8 - num;
                }
                whichPieceNum = (--num);
                break;
            case 4:
                //垂直翻转
                if(num==1 || num==5){
                    num = 6 - num;
                    //时钟1到5点钟属于钟面的右边，1点变5点
                }else if((2<=num && num<=4) || (6<=num && num<=8)){
                    num = 10 - num;
                    //时钟1到5点钟属于钟面的右边，1点变5点
                }
                whichPieceNum = (--num);


        }

    }

}
