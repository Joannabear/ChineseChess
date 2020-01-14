package finalchess;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

class Url {
	JTextArea a1 = Chess.a1;
	//String text = Chess.textbox;
	public static String text = "s1072032";
	public static String roomid = null;

	public Url() throws InterruptedException {
		try {
			String string = null;
			URL u = new URL("http://140.138.147.44:6004/you_r_fired/start?SID=" + text);
			URLConnection connection = u.openConnection();
			HttpURLConnection htCon = (HttpURLConnection) connection;
			int code = htCon.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(htCon.getInputStream()));
				String inputLine = null;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					a1.append(inputLine + "\r\n");
					String tmp = inputLine;
					String splitStr = null;
					int k = tmp.indexOf(" "); // 找分隔符的位置
					splitStr = tmp.substring(0, k); // 找到分隔符，擷取子字串
					roomid = splitStr;
				}
				in.close();
			}

			u = new URL("http://140.138.147.44:6004/you_r_fired/select_room?SID=" + text + "&room_id=" + roomid);
			connection = u.openConnection();
			htCon = (HttpURLConnection) connection;
			code = htCon.getResponseCode();
			if (code == HttpURLConnection.HTTP_OK) {
				BufferedReader in = new BufferedReader(new InputStreamReader(htCon.getInputStream()));
				String inputLine = null;
				while ((inputLine = in.readLine()) != null) {
					System.out.println(inputLine);
					a1.append(inputLine + "\r\n");
					string = inputLine;
				}
				in.close();
			}

			if (string.equals("success")) {
				u = new URL("http://140.138.147.44:6004/you_r_fired/wait?SID=" + text + "&room_id=" + roomid);
				connection = u.openConnection();
				htCon = (HttpURLConnection) connection;
				code = htCon.getResponseCode();
				if (code == HttpURLConnection.HTTP_OK) {
					BufferedReader in = new BufferedReader(new InputStreamReader(htCon.getInputStream()));
					String inputLine = null;
					while ((inputLine = in.readLine()) != null) {
						System.out.println(inputLine);
						a1.append(inputLine + "\r\n");
						string = inputLine;
					}
					in.close();
				}
			}

			int j = string.indexOf(" ");
			String splitStr1 = string.substring(0, j);
			string = string.substring(j + 1);
			String splitStr2 = string.substring(0, j);
			string = string.substring(j + 1);
			String splitStr3 = string.substring(0, j);
			string = string.substring(j + 1);
			String splitStr4 = string.substring(0, j);
			string = string.substring(j + 1);
			if (splitStr4.equals("n") == false) {
				String splitStr5 = string.substring(0, j);
				string = string.substring(j + 1);
				String splitStr6 = string.substring(0, j);
			}

			while ((splitStr1.equals("0") || splitStr1.equals("2")) && (splitStr2 != splitStr3)) {
				new WaitUrl(text, roomid);
				Thread.sleep(5000);
			}

		} catch (MalformedURLException e) {
		} catch (IOException e) {
		}
	}
}

class WaitUrl {
	public static String splitStr1 = null;
	public static String splitStr2 = null;
	public static String splitStr3 = null;
	public static String splitStr4 = null;
	public static String splitStr5 = null;
	public static String splitStr6 = null;
	public static int s5 = 0;
	public static int s6 = 0;
	JTextArea a1 = Chess.a1;

	public WaitUrl(String name, String roomid) throws IOException {
		String string = null;
		URL u = new URL("http://140.138.147.44:6004/you_r_fired/wait?SID=" + name + "&room_id=" + roomid);
		URLConnection connection = u.openConnection();
		HttpURLConnection htCon = (HttpURLConnection) connection;
		int code = htCon.getResponseCode();
		if (code == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(htCon.getInputStream()));
			String inputLine = null;
			while ((inputLine = in.readLine()) != null) {
				string = inputLine;
				System.out.println(string);
				a1.append(string + "\r\n");
			}
			in.close();
		}

		int j = string.indexOf(" ");
		splitStr1 = string.substring(0, j);
		string = string.substring(j + 1);
		splitStr2 = string.substring(0, j);
		string = string.substring(j + 1);
		splitStr3 = string.substring(0, j);
		string = string.substring(j + 1);
		splitStr4 = string.substring(0, j);
		string = string.substring(j + 1);
		if (splitStr4.equals("n") == false) {
			splitStr5 = string.substring(0, j);
			s5 = Integer.parseInt((String) splitStr5);
			string = string.substring(j + 1);
			splitStr6 = string.substring(0, j);
			s6 = Integer.parseInt((String) splitStr6);
		}

	}
}

class Chess extends JFrame implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ChessBoard board = null;
	Container con = null;
	ChessPoint[][] point;
	static String splitStr2 = WaitUrl.splitStr2;
	static boolean redrun;
	static boolean blackrun;
	public static JTextArea a1;
	public static String textbox;
	public static JTextField t1;
	int i = 0;

	public void setTitle(String title) {
		super.setTitle("yzu1072032_chess");
	}

	public Chess() {
		board = new ChessBoard(50, 50, 9, 10);
		setTitle("yzu1072032_chess");
		con = getContentPane();
		a1 = new JTextArea();
		t1 = new JTextField();
		JButton b1 = new JButton("名字確定");
		JSplitPane bottom = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, t1, b1);
		bottom.setEnabled(false);
		bottom.setDividerLocation(500);
		JSplitPane top = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, board, a1);
		top.setEnabled(false);
		top.setDividerLocation(500);
		JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true, top, bottom);
		split.setEnabled(false);
		split.setDividerLocation(550);
		b1.addActionListener(this);

		con.add(split, BorderLayout.CENTER);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setVisible(true);
		setBounds(60, 100, 650, 650); // (X軸上的起點, Y軸上的起點, 寬度, 高度)
		con.validate();
		validate();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		textbox = t1.getText();
	}
	
	public static void main(String args[]) throws InterruptedException {
		new Chess();
		new Url();
	}
}

class ChessBoard extends JPanel implements MouseListener, MouseMotionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ChessPoint point[][];
	public int Width, Height; // 點的水平距離和垂直距離
	int xlength, ylength; // 棋盤行與列數
	int x, y;
	boolean move = false;
	static int s5 = WaitUrl.s5;
	static int s6 = WaitUrl.s6;
	public String redteam = "red", blackteam = "black";

	int startX, startY;
	int startI, startJ;
	public boolean redrun = false, blackrun = false;
	ChessRule rule = null;

	public ChessBoard(int w, int h, int r, int c) { // (棋格寬度、棋格高度、X軸長、Y軸長)
		super();
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this); // 跟蹤鼠標移動和鼠標拖動
		Width = w;
		Height = h;
		xlength = r;
		ylength = c;

		point = new ChessPoint[r + 5][c + 5];
		for (int i = 1; i <= r; i++) {
			for (int j = 1; j <= c; j++) {
				point[i][j] = new ChessPoint(i * Width, j * Height, false);
			}
		}
		rule = new ChessRule(this, point);

		Color colorred = new Color(204, 0, 0);
		ChessPiece blackChariot1 = new ChessPiece(107, "車", Color.black, w - 4, h - 4, this);
		blackChariot1.setCategory(blackteam);
		ChessPiece blackChariot2 = new ChessPiece(108, "車", Color.black, w - 4, h - 4, this);
		blackChariot2.setCategory(blackteam);
		ChessPiece blackHorse1 = new ChessPiece(105, "馬", Color.black, w - 4, h - 4, this);
		blackHorse1.setCategory(blackteam);
		ChessPiece blackHorse2 = new ChessPiece(106, "馬", Color.black, w - 4, h - 4, this);
		blackHorse2.setCategory(blackteam);
		ChessPiece blackCannon1 = new ChessPiece(109, "砲", Color.black, w - 4, h - 4, this);
		blackCannon1.setCategory(blackteam);
		ChessPiece blackCannon2 = new ChessPiece(110, "砲", Color.black, w - 4, h - 4, this);
		blackCannon2.setCategory(blackteam);
		ChessPiece blackElephant1 = new ChessPiece(103, "象", Color.black, w - 4, h - 4, this);
		blackElephant1.setCategory(blackteam);
		ChessPiece blackElephant2 = new ChessPiece(104, "象", Color.black, w - 4, h - 4, this);
		blackElephant2.setCategory(blackteam);
		ChessPiece blackGuard1 = new ChessPiece(101, "士", Color.black, w - 4, h - 4, this);
		blackGuard1.setCategory(blackteam);
		ChessPiece blackGuard2 = new ChessPiece(102, "士", Color.black, w - 4, h - 4, this);
		blackGuard2.setCategory(blackteam);
		ChessPiece blackKing = new ChessPiece(100, "將", Color.black, w - 4, h - 4, this);
		blackKing.setCategory(blackteam);
		ChessPiece blackSoldier1 = new ChessPiece(111, "卒", Color.black, w - 4, h - 4, this);
		blackSoldier1.setCategory(blackteam);
		ChessPiece blackSoldier2 = new ChessPiece(112, "卒", Color.black, w - 4, h - 4, this);
		blackSoldier2.setCategory(blackteam);
		ChessPiece blackSoldier3 = new ChessPiece(113, "卒", Color.black, w - 4, h - 4, this);
		blackSoldier3.setCategory(blackteam);
		ChessPiece blackSoldier4 = new ChessPiece(114, "卒", Color.black, w - 4, h - 4, this);
		blackSoldier4.setCategory(blackteam);
		ChessPiece blackSoldier5 = new ChessPiece(115, "卒", Color.black, w - 4, h - 4, this);
		blackSoldier5.setCategory(blackteam);

		ChessPiece redKing = new ChessPiece(0, "帥", colorred, w - 4, h - 4, this);
		redKing.setCategory(redteam);
		ChessPiece redGuard1 = new ChessPiece(1, "仕", colorred, w - 4, h - 4, this);
		redGuard1.setCategory(redteam);
		ChessPiece redGuard2 = new ChessPiece(2, "仕", colorred, w - 4, h - 4, this);
		redGuard2.setCategory(redteam);
		ChessPiece redChariot1 = new ChessPiece(7, "俥", colorred, w - 4, h - 4, this);
		redChariot1.setCategory(redteam);
		ChessPiece redChariot2 = new ChessPiece(8, "俥", colorred, w - 4, h - 4, this);
		redChariot2.setCategory(redteam);
		ChessPiece redCannon1 = new ChessPiece(9, "炮", colorred, w - 4, h - 4, this);
		redCannon1.setCategory(redteam);
		ChessPiece redCannon2 = new ChessPiece(10, "炮", colorred, w - 4, h - 4, this);
		redCannon2.setCategory(redteam);
		ChessPiece redElephant1 = new ChessPiece(3, "相", colorred, w - 4, h - 4, this);
		redElephant1.setCategory(redteam);
		ChessPiece redElephant2 = new ChessPiece(4, "相", colorred, w - 4, h - 4, this);
		redElephant2.setCategory(redteam);
		ChessPiece redHorse1 = new ChessPiece(5, "傌", colorred, w - 4, h - 4, this);
		redHorse1.setCategory(redteam);
		ChessPiece redHorse2 = new ChessPiece(6, "傌", colorred, w - 4, h - 4, this);
		redHorse2.setCategory(redteam);
		ChessPiece redSoldier1 = new ChessPiece(11, "兵", colorred, w - 4, h - 4, this);
		redSoldier1.setCategory(redteam);
		ChessPiece redSoldier2 = new ChessPiece(12, "兵", colorred, w - 4, h - 4, this);
		redSoldier2.setCategory(redteam);
		ChessPiece redSoldier3 = new ChessPiece(13, "兵", colorred, w - 4, h - 4, this);
		redSoldier3.setCategory(redteam);
		ChessPiece redSoldier4 = new ChessPiece(14, "兵", colorred, w - 4, h - 4, this);
		redSoldier4.setCategory(redteam);
		ChessPiece redSoldier5 = new ChessPiece(15, "兵", colorred, w - 4, h - 4, this);
		redSoldier5.setCategory(redteam);

		point[1][10].setPiece(blackChariot1, this);
		point[2][10].setPiece(blackHorse1, this);
		point[3][10].setPiece(blackElephant1, this);
		point[4][10].setPiece(blackGuard1, this);
		point[5][10].setPiece(blackKing, this);
		point[6][10].setPiece(blackGuard2, this);
		point[7][10].setPiece(blackElephant2, this);
		point[8][10].setPiece(blackHorse2, this);
		point[9][10].setPiece(blackChariot2, this);
		point[2][8].setPiece(blackCannon1, this);
		point[8][8].setPiece(blackCannon2, this);
		point[1][7].setPiece(blackSoldier1, this);
		point[3][7].setPiece(blackSoldier2, this);
		point[5][7].setPiece(blackSoldier3, this);
		point[7][7].setPiece(blackSoldier4, this);
		point[9][7].setPiece(blackSoldier5, this);
		point[1][1].setPiece(redChariot1, this);
		point[2][1].setPiece(redHorse1, this);
		point[3][1].setPiece(redElephant1, this);
		point[4][1].setPiece(redGuard1, this);
		point[5][1].setPiece(redKing, this);
		point[6][1].setPiece(redGuard2, this);
		point[7][1].setPiece(redElephant2, this);
		point[8][1].setPiece(redHorse2, this);
		point[9][1].setPiece(redChariot2, this);
		point[2][3].setPiece(redCannon1, this);
		point[8][3].setPiece(redCannon2, this);
		point[1][4].setPiece(redSoldier1, this);
		point[3][4].setPiece(redSoldier2, this);
		point[5][4].setPiece(redSoldier3, this);
		point[7][4].setPiece(redSoldier4, this);
		point[9][4].setPiece(redSoldier5, this);
	}

	@Override
	public void paintComponent(Graphics g) { // 畫棋盤
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(1.5f)); // 線條加粗

		Color color1 = new Color(242, 170, 65);
		Color color2 = new Color(206, 92, 0);
		Font f = new Font("標楷體", Font.BOLD, 30);
		g.setFont(f);
		g.setColor(color2);
		g.fillRect(10, 10, 480, 530); // 外圈填色
		g.setColor(Color.black);
		g.drawRect(10, 10, 480, 530); // 外圈線
		g.setColor(color1);
		g.fillRect(20, 20, 460, 510); // 內圈填色
		g.setColor(Color.black);
		g.drawRect(20, 20, 460, 510); // 內圈線

		for (int j = 1; j <= ylength; j++) {
			g.drawLine(point[1][j].x, point[1][j].y, point[xlength][j].x, point[xlength][j].y); // 直線的起點座標，终點座標
		}
		for (int i = 1; i <= xlength; i++) {
			if (i != 1 && i != xlength) {
				g.drawLine(point[i][1].x, point[i][1].y, point[i][ylength - 5].x, point[i][ylength - 5].y);
				g.drawLine(point[i][ylength - 4].x, point[i][ylength - 4].y, point[i][ylength].x, point[i][ylength].y); // 畫直線，多减一個格子整體向上平移一行
			} else {
				g.drawLine(point[i][1].x, point[i][1].y, point[i][ylength].x, point[i][ylength].y);
			}
		}

		g.drawLine(point[4][1].x, point[4][1].y, point[6][3].x, point[6][3].y);
		g.drawLine(point[6][1].x, point[6][1].y, point[4][3].x, point[4][3].y);
		g.drawLine(point[4][8].x, point[4][8].y, point[6][ylength].x, point[6][ylength].y);
		g.drawLine(point[4][ylength].x, point[4][ylength].y, point[6][8].x, point[6][8].y);
	}

	public void mousePressed(MouseEvent e) { // 負責鼠標拖動棋子過程中的動作回應
		ChessPiece piece = null;
		Rectangle rect = null;
		if (e.getSource() == this) // 如果得到的事件源是這個class的實例本身
			move = false;
		if (move == false)
			if (e.getSource() instanceof ChessPiece) { // 如果事件源是class ChessPiece的實例
				piece = (ChessPiece) e.getSource();
				startX = piece.getBounds().x;
				startY = piece.getBounds().y;

				rect = piece.getBounds();
				for (int i = 1; i <= xlength; i++) {
					for (int j = 1; j <= ylength; j++) {
						int x = point[i][j].getX(); // 返回鼠標點擊的X
						int y = point[i][j].getY();
						if (rect.contains(x, y)) { // 點擊了
							startI = i;
							startJ = j;
							break;
						}
					}
				}
			}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseDragged(MouseEvent e) {
		ChessPiece piece = null;
		if (e.getSource() instanceof ChessPiece) {
			piece = (ChessPiece) e.getSource(); // 將piece值為鼠標事件的發生源
			move = true;
			e = SwingUtilities.convertMouseEvent(piece, e, this); // 將鼠標事件轉化
		}

		if (e.getSource() == this) {
			if (move && piece != null) {
				x = e.getX(); // 得到這個鼠標事件相對於產生它的组件的Ｘ坐標
				y = e.getY();
				if (redrun && ((piece.Category()).equals(redteam))) {
					piece.setLocation(x - piece.getWidth() / 2, y - piece.getHeight() / 2); // piece移到新的位置，托起時滑鼠對準棋子圓心
				}
				if (blackrun && (piece.Category().equals(blackteam))) {
					piece.setLocation(x - piece.getWidth() / 2, y - piece.getHeight() / 2);
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		ChessPiece piece = null;
		String text = Chess.textbox;
		String roomid = Url.roomid;
		String splitStr1 = WaitUrl.splitStr1;
		String splitStr2 = WaitUrl.splitStr2;
		String splitStr3 = WaitUrl.splitStr3;
		move = false;
		Rectangle rect = null;

		if ((splitStr1.equals("0")) && (splitStr2.equals("0"))) {
			redrun = true;
		}
		if ((splitStr1.equals("0")) && (splitStr2.equals("1"))) {
			blackrun = true;
		}

		if (e.getSource() instanceof ChessPiece) {
			piece = (ChessPiece) e.getSource();
			rect = piece.getBounds();

			e = SwingUtilities.convertMouseEvent(piece, e, this);
		}
		if (e.getSource() == this) {
			boolean containChessPoint = false;
			int x = 0, y = 0;
			int m = 0, n = 0;
			if (piece != null) {
				for (int i = 1; i <= xlength; i++) {
					for (int j = 1; j <= ylength; j++) {
						x = point[i][j].getX();
						y = point[i][j].getY();
						if (rect.contains(x, y)) {
							containChessPoint = true;
							m = i;
							n = j;
							break;
						}
					}
				}
			}
			if (piece != null && containChessPoint) {
				Color pieceColor = piece.getColor();
				if (point[m][n].isPiece()) {
					Color c = (point[m][n].getPiece()).getColor();
					if (pieceColor.getRGB() == c.getRGB()) {
						piece.setLocation(startX, startY);
						(point[startI][startJ]).setFlag(true);
					} else {
						boolean ok = rule.movePieceRule(piece, startI, startJ, m, n);
						if (ok) {
							ChessPiece pieceRemoved = point[m][n].getPiece();
							point[m][n].reMovePiece(pieceRemoved, this);
							point[m][n].setPiece(piece, this);
							(point[startI][startJ]).setFlag(false);
							repaint();
						} else {
							piece.setLocation(startX, startY);
							(point[startI][startJ]).setFlag(true);
						}
					}
				} else {
					boolean ok = rule.movePieceRule(piece, startI, startJ, m, n);
					if (ok) {
						point[m][n].setPiece(piece, this);
						(point[startI][startJ]).setFlag(false);
					} else {
						piece.setLocation(startX, startY);
						(point[startI][startJ]).setFlag(true);
					}
				}
				if ((point[startI][startJ]).flag == false) { // 有移動棋子時
					URL u = null;
					try {
						u = new URL("http://140.138.147.44:6004/you_r_fired/play?SID=" + text + "&room_id=" + roomid
								+ "&type=" + piece.num + "&x=" + (m - 1) + "&y=" + (n - 1));
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
					URLConnection connection = null;
					try {
						connection = u.openConnection();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					HttpURLConnection htCon = (HttpURLConnection) connection;
					try {
						int code = htCon.getResponseCode();
						if (code == HttpURLConnection.HTTP_OK) {
							new WaitUrl(text, roomid);
							while ((splitStr1.equals("0") || splitStr1.equals("2")) && (splitStr2 != splitStr3)) {
								new WaitUrl(text, roomid);
								Thread.sleep(5000);
							}
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
			if (piece != null && !containChessPoint) {
				piece.setLocation(startX, startY);
				(point[startI][startJ]).setFlag(true);
			}
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}
}

class ChessPiece extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	Color foreColor;
	String colorcatrgory = null;
	ChessBoard board = null;
	int width, height;
	int num;

	public ChessPiece(int num, String name, Color fc, int width, int height, ChessBoard board) {
		this.num = num;
		this.name = name;
		this.board = board;
		this.width = width;
		this.height = height;
		foreColor = fc;
		setSize(width, height);
		addMouseMotionListener(board);
		addMouseListener(board);
	}

	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(2f)); // 線條加粗

		Color color = new Color(254, 218, 164);
		g.setColor(color);
		g.fillOval(2, 2, width - 2, height - 2); // 填充圓形（左右，上下，寬，高）

		g.setColor(foreColor); // 象棋的字體颜色
		g.setFont(new Font("標楷體", Font.PLAIN, 27)); // Font(字體名，字型，字號)
		g.drawString(name, 10, height - 12);
		g.setColor(Color.black);
		g.drawOval(2, 2, width - 2, height - 2);
		g.setColor(foreColor);
		g.drawOval(5, 5, width - 8, height - 8);

	}

	public int getNum() {
		return num;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getName() {
		return name;
	}

	public Color getColor() {
		return foreColor;
	}

	public void setCategory(String category) {
		colorcatrgory = category;
	}

	public String Category() {
		return colorcatrgory;
	}
}

class ChessPoint {
	int x, y;
	boolean flag;
	ChessPiece piece = null;
	ChessBoard board = null;

	public ChessPoint(int x, int y, boolean boo) {
		this.x = x;
		this.y = y;
		flag = boo;
	}

	public boolean isPiece() {
		return flag;
	}

	public void setFlag(boolean boo) {
		flag = boo;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setPiece(ChessPiece piece, ChessBoard board) {
		this.board = board;
		this.piece = piece;
		board.add(piece);
		int w = (board.Width);
		int h = (board.Height);
		piece.setBounds(x - w / 2, y - h / 2, w, h); // （棋格寬度、棋格高度、X軸長、Y軸長）
		flag = true;
	}

	public ChessPiece getPiece() {
		return piece;
	}

	public void reMovePiece(ChessPiece piece, ChessBoard board) {
		this.board = board;
		this.piece = piece;
		board.remove(piece);
		board.validate();
		flag = false;
	}
}

class ChessRule {
	ChessBoard board = null;
	ChessPiece piece = null;
	ChessPoint point[][];
	int startI, startJ, endI, endJ;

	public ChessRule(ChessBoard board, ChessPoint point[][]) {
		this.board = board;
		this.point = point;
	}

	public boolean movePieceRule(ChessPiece piece, int startI, int startJ, int endI, int endJ) { // 移動棋子的規則
		this.piece = piece;
		this.startI = startI;
		this.startJ = startJ;
		this.endI = endI;
		this.endJ = endJ;
		int minI = Math.min(startI, endI);
		int maxI = Math.max(startI, endI);
		int minJ = Math.min(startJ, endJ);
		int maxJ = Math.max(startJ, endJ);
		boolean Flag = false; // 判斷棋子是否可以走
		if (piece.getName().equals("車") || piece.getName().equals("俥")) {
			// （横線、直線均可）無子阻擋便可走（以下半段是否有子阻攔）
			if (startI == endI) {
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isPiece()) {
						Flag = false;
						break;
					}
				}
				if (j == maxJ) {
					Flag = true;
				}
			} else if (startJ == endJ) {
				int i = 0;
				for (i = minI + 1; i <= maxI - 1; i++) {
					if (point[i][startJ].isPiece()) {
						Flag = false;
						break;
					}
				}
				if (i == maxI) {
					Flag = true;
				}
			} else {
				Flag = false;
			}
		} else if (piece.getName().equals("馬") || piece.getName().equals("傌")) {
			// 馬走日（先橫著或直著走一格＋斜着走一個對角線）若行進的方向有子阻擋則不能行進
			int xAxle = Math.abs(startI - endI); // Ｘ軸
			int yAxle = Math.abs(startJ - endJ);

			if (xAxle == 2 && yAxle == 1) {
				if (endI > startI) {
					if (point[startI + 1][startJ].isPiece()) {
						Flag = false;
					} else {
						Flag = true;
					}
				}
				if (endI < startI) {
					if (point[startI - 1][startJ].isPiece()) {
						Flag = false;
					} else {
						Flag = true;
					}
				}

			} else if (xAxle == 1 && yAxle == 2) {
				if (endJ > startJ) {
					if (point[startI][startJ + 1].isPiece()) {
						Flag = false;
					} else {
						Flag = true;
					}
				}
				if (endJ < startJ) {
					if (point[startI][startJ - 1].isPiece()) {
						Flag = false;
					} else {
						Flag = true;
					}
				}

			} else {
				Flag = false;
			}
		} else if (piece.getName().equals("相")) {
			// 相走田（不能過河，“田”字中間有子不能走）保家衛國
			int centerI = (startI + endI) / 2;
			int centerJ = (startJ + endJ) / 2;
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (xAxle == 2 && yAxle == 2 && endJ <= 5) {
				if (point[centerI][centerJ].isPiece()) {
					Flag = false;
				} else {
					Flag = true;
				}
			} else {
				Flag = false;
			}
		} else if (piece.getName().equals("象")) {
			// 同“相”
			int centerI = (startI + endI) / 2;
			int centerJ = (startJ + endJ) / 2;
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (xAxle == 2 && yAxle == 2 && endJ >= 6) {
				if (point[centerI][centerJ].isPiece()) {
					Flag = false;
				} else {
					Flag = true;
				}
			} else {
				Flag = false;
			}
		} else if (piece.getName().equals("炮") || piece.getName().equals("砲")) {
			// 隨意走（同“車”），要吃子的時候必須有“橋”
			int number = 0;
			if (startI == endI) {
				int j = 0;
				for (j = minJ + 1; j <= maxJ - 1; j++) {
					if (point[startI][j].isPiece()) {
						number++;
					}
				}
				if (number > 1) {
					Flag = false;
				} else if (number == 1) {
					if (point[endI][endJ].isPiece()) {
						Flag = true;
					}
				} else if (number == 0 && !point[endI][endJ].isPiece()) {
					Flag = true;
				}
			} else if (startJ == endJ) {
				int i = 0;
				for (i = minI + 1; i <= maxI - 1; i++) {
					if (point[i][startJ].isPiece()) {
						number++;
					}
				}
				if (number > 1) {
					Flag = false;
				} else if (number == 1) {
					if (point[endI][endJ].isPiece()) {
						Flag = true;
					}
				} else if (number == 0 && !point[endI][endJ].isPiece()) {
					Flag = true;
				}
			} else {
				Flag = false;
			}
		} else if (piece.getName().equals("卒")) {
			// 在己方只能前進，（過河后）在敵方可走前、左、右（一次走一步）
			int xAxle = Math.abs(startI - endI);
			if (endJ >= 6) {
				if (startJ - endJ == 1 && xAxle == 0) {
					Flag = true;
				} else {
					Flag = false;
				}
			} else if (endJ <= 5) {
				if ((startJ - endJ == 1) && (xAxle == 0)) {
					Flag = true;
				} else if ((endJ - startJ == 0) && (xAxle == 1)) {
					Flag = true;
				} else {
					Flag = false;
				}
			}
		} else if (piece.getName().equals("兵")) {
			// 同“卒”
			int xAxle = Math.abs(startI - endI);
			if (endJ <= 5) {
				if (endJ - startJ == 1 && xAxle == 0) {
					Flag = true;
				} else {
					Flag = false;
				}
			} else if (endJ >= 6) {
				if ((endJ - startJ == 1) && (xAxle == 0)) {
					Flag = true;
				} else if ((endJ - startJ == 0) && (xAxle == 1)) {
					Flag = true;
				} else {
					Flag = false;
				}
			}
		} else if (piece.getName().equals("士") || (piece.getName().equals("仕"))) {
			// 只能走己方九宫格中的斜線
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI <= 6 && endI >= 4 && xAxle == 1 && yAxle == 1) {
				Flag = true;
			} else {
				Flag = false;
			}
		} else if ((piece.getName().equals("帥")) || (piece.getName().equals("將"))) {
			// 只能走己方九宫格中的直線
			int xAxle = Math.abs(startI - endI);
			int yAxle = Math.abs(startJ - endJ);
			if (endI <= 6 && endI >= 4) {
				if ((xAxle == 1 && yAxle == 0) || (xAxle == 0 && yAxle == 1)) {
					Flag = true;
				} else {
					Flag = false;
				}
			} else {
				Flag = false;
			}
		}
		return Flag;
	}
}