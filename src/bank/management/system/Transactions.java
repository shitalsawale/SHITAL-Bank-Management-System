package bank.management.system;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.util.ArrayDeque;
import java.util.Queue;

public class Transactions extends JFrame implements ActionListener {

    JButton deposit, cashWithdraw, fastCash, miniStatement, pinChange, balanceEnquiry, exit;
    String pinNumber;

    public Transactions(String pinNumber) {
        this.pinNumber = pinNumber;

        setLayout(null);
        setTitle("TRANSACTION MENU");

        try {
            // load image from resources
            InputStream is = ClassLoader.getSystemResourceAsStream("icons/atm.jpg");
            if (is == null) {
                JOptionPane.showMessageDialog(null, "icons/atm.jpg not found in resources.");
                return;
            }
            BufferedImage bimg = ImageIO.read(is);
            ImageIcon icon = new ImageIcon(bimg);
            int imgW = icon.getIconWidth();
            int imgH = icon.getIconHeight();

            // set frame size to image size so coordinates align exactly
            setSize(imgW, imgH);
            setLocationRelativeTo(null);

            // JLabel holds the background image
            JLabel background = new JLabel(icon);
            background.setBounds(0, 0, imgW, imgH);
            background.setLayout(null); // we will position overlay relative to image
            add(background);

            // try to detect the large dark region (black screen) automatically
            Rectangle screenRect = detectDarkRegion(bimg);

            // fallback rectangle if detection fails (tweak here if needed)
            if (screenRect == null) {
                // default approximate area (you can adjust these if needed)
                screenRect = new Rectangle(imgW/6, imgH/4, imgW*2/3, imgH/3);
            }

            // small padding inside the detected area so buttons don't touch edges
            int padding = Math.max(8, Math.min(screenRect.width/30, screenRect.height/20));
            Rectangle inner = new Rectangle(
                    screenRect.x + padding,
                    screenRect.y + padding,
                    screenRect.width - 2*padding,
                    screenRect.height - 2*padding
            );

            // transparent overlay panel positioned exactly over the black screen area
            JPanel overlay = new JPanel(null);
            overlay.setOpaque(false); // show background image
            overlay.setBounds(inner);
            background.add(overlay);

            // button sizing and layout inside overlay
            int btnW = Math.min(180, inner.width/2 - 20);
            int btnH = Math.max(28, inner.height / 7);
            int centerX = inner.width / 2;
            int leftX = centerX - btnW - 12;
            int rightX = centerX + 12;
            int startY = Math.max(12, inner.height/8);
            int gapY = btnH + Math.max(6, inner.height/30);

            deposit = createButton("Deposit", leftX, startY, btnW, btnH);
            overlay.add(deposit);

            cashWithdraw = createButton("Cash Withdraw", rightX, startY, btnW, btnH);
            overlay.add(cashWithdraw);

            fastCash = createButton("Fast Cash", leftX, startY + gapY, btnW, btnH);
            overlay.add(fastCash);

            miniStatement = createButton("Mini Statement", rightX, startY + gapY, btnW, btnH);
            overlay.add(miniStatement);

            pinChange = createButton("Pin Change", leftX, startY + 2*gapY, btnW, btnH);
            overlay.add(pinChange);

            balanceEnquiry = createButton("Balance Enquiry", rightX, startY + 2*gapY, btnW, btnH);
            overlay.add(balanceEnquiry);

            exit = createButton("Exit", (inner.width - btnW) / 2, startY + 3*gapY, btnW, btnH);
            overlay.add(exit);

            // show frame
            setUndecorated(true);   // optional: remove window border for ATM look
            setVisible(true);

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading image or building UI:\n" + ex.getMessage());
        }
    }

    // helper: make consistent buttons
    private JButton createButton(String text, int x, int y, int w, int h) {
        JButton b = new JButton(text);
        b.setBounds(x, y, w, h);
        b.setFont(new Font("SansSerif", Font.PLAIN, Math.max(12, h/2 - 4)));
        b.addActionListener(this);
        return b;
    }

    // Action handling - open corresponding windows (you must have these classes)
    public void actionPerformed(ActionEvent ae) {
        Object src = ae.getSource();
        if (src == exit) {
            setVisible(false);
            new Login().setVisible(true); // your Login frame
        } else if (src == deposit) {
            setVisible(false);
            new Deposit(pinNumber).setVisible(true);
        } else if (src == cashWithdraw) {
            setVisible(false);
            new Withdrawl(pinNumber).setVisible(true);
        } else if (src == fastCash) {
            setVisible(false);
            new FastCash(pinNumber).setVisible(true);
        } else if (src == miniStatement) {
            new MiniStatement(pinNumber).setVisible(true);
        } else if (src == pinChange) {
            setVisible(false);
            new PinChange(pinNumber).setVisible(true);
        } else if (src == balanceEnquiry) {
            setVisible(false);
            new BalanceEnquiry(pinNumber).setVisible(true);
        }
    }

    /**
     * Detects a large dark region (approx black display) inside the image using block-grid
     * segmentation and connected component detection on the block-grid. Returns pixel Rectangle
     * in image coordinates or null if nothing found.
     */
    private Rectangle detectDarkRegion(BufferedImage img) {
        int w = img.getWidth();
        int h = img.getHeight();

        // block size: adapt to image size
        int block = Math.max(8, Math.min(16, Math.min(w, h)/50));
        int cols = w / block;
        int rows = h / block;

        boolean[][] dark = new boolean[rows][cols];
        // threshold for darkness (0..255). tune if needed
        int thresh = 70;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                long sum = 0;
                int count = 0;
                for (int yy = r*block; yy < Math.min(h, (r+1)*block); yy++) {
                    for (int xx = c*block; xx < Math.min(w, (c+1)*block); xx++) {
                        int rgb = img.getRGB(xx, yy);
                        int rr = (rgb>>16) & 0xff;
                        int gg = (rgb>>8) & 0xff;
                        int bb = rgb & 0xff;
                        int lum = (rr + gg + bb) / 3;
                        sum += lum;
                        count++;
                    }
                }
                int avg = (int)(sum / Math.max(1, count));
                dark[r][c] = avg <= thresh;
            }
        }

        boolean[][] vis = new boolean[rows][cols];
        Rectangle best = null;
        int bestArea = 0;

        // BFS components on block grid
        int[] dr = {1,-1,0,0};
        int[] dc = {0,0,1,-1};
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (dark[r][c] && !vis[r][c]) {
                    int minR=r, maxR=r, minC=c, maxC=c;
                    Queue<int[]> q = new ArrayDeque<>();
                    q.add(new int[]{r,c});
                    vis[r][c]=true;
                    while (!q.isEmpty()) {
                        int[] p = q.poll();
                        int pr = p[0], pc = p[1];
                        minR = Math.min(minR, pr);
                        maxR = Math.max(maxR, pr);
                        minC = Math.min(minC, pc);
                        maxC = Math.max(maxC, pc);
                        for (int k=0;k<4;k++) {
                            int nr = pr+dr[k], nc = pc+dc[k];
                            if (nr>=0 && nr<rows && nc>=0 && nc<cols && dark[nr][nc] && !vis[nr][nc]) {
                                vis[nr][nc] = true;
                                q.add(new int[]{nr,nc});
                            }
                        }
                    }
                    int area = (maxR-minR+1) * (maxC-minC+1);
                    // choose the biggest dark connected block (likely the screen)
                    if (area > bestArea) {
                        bestArea = area;
                        int x = Math.max(0, minC * block);
                        int y = Math.max(0, minR * block);
                        int W = Math.min(w - x, (maxC-minC+1) * block);
                        int H = Math.min(h - y, (maxR-minR+1) * block);
                        best = new Rectangle(x, y, W, H);
                    }
                }
            }
        }

        // require a reasonable size to avoid tiny dark areas
        if (best != null) {
            int minPixels = Math.min(w,h) * 50; // minimal reasonable area - tweak if needed
            if (best.width * best.height < minPixels) {
                return null;
            }
        }

        return best;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Transactions("").setVisible(true));
    }
}
