
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Random;
import java.awt.Color;
import java.awt.Canvas;

public class SortViz {
    public static void main(String[] args) {
        Frame frame = new Frame("Sorting Viz");
        frame.setSize(1480, 1080);

        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        int n = 800;
        int[] heights = new int[n];
        int offset = 1;
        int barwidth = 1;
        int gap = 0;

        for (int i = 0; i < n; i++) {
            heights[i] = i + 1;
        }

        Random rand = new Random();
        for (int i = 0; i < heights.length; i++) {
            int randomIndexToSwap = rand.nextInt(heights.length);
            int temp = heights[randomIndexToSwap];
            heights[randomIndexToSwap] = heights[i];
            heights[i] = temp;
        }

        Canvas canvas = new Canvas() {
            private Image bufferImage;
            private Graphics bufferGraphics;

            @Override
            public void update(Graphics g) {
                paint(g);
            }

            @Override
            public void paint(Graphics g) {
                if (bufferImage == null) {
                    bufferImage = createImage(getWidth(), getHeight());
                    bufferGraphics = bufferImage.getGraphics();
                }

                bufferGraphics.setColor(getBackground());
                bufferGraphics.fillRect(0, 0, getWidth(), getHeight());

                for (int i = 0; i < n; i++) {
                    bufferGraphics.setColor(Color.GREEN);
                    bufferGraphics.fillRect(10 + offset + i * (barwidth + gap), 900 - heights[i] * 1, barwidth,
                            heights[i] * 1);
                }

                g.drawImage(bufferImage, 0, 0, this);
            }
        };

        frame.add(canvas);
        frame.setBackground(Color.BLACK);
        frame.setVisible(true);

        // UNCOMMENT THE ALGORITHM YOU WANT TO WATCH!!!!
        new Thread(() -> selectionSort(heights, n, canvas)).start();

        // new Thread(() -> bubbleSort(heights, n, canvas)).start();
        // new Thread(() -> mergeSort(heights, 0, n - 1, canvas)).start();

    }

    static void bubbleSort(int[] arr, int n, Canvas canvas) {
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;

                    canvas.repaint();

                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            if (!swapped)
                break;
        }
    }

    static void mergeSort(int[] arr, int l, int r, Canvas canvas) {
        if (l < r) {
            int m = (l + r) / 2;
            canvas.repaint();

            mergeSort(arr, l, m, canvas);
            canvas.repaint();
            mergeSort(arr, m + 1, r, canvas);

            merge(arr, l, m, r, canvas);

            canvas.repaint();

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static void merge(int[] arr, int l, int m, int r, Canvas canvas) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int[] L = new int[n1];
        int[] R = new int[n2];

        for (int i = 0; i < n1; ++i) {
            L[i] = arr[l + i];
            canvas.repaint();
        }
        for (int j = 0; j < n2; ++j) {
            R[j] = arr[m + 1 + j];
            canvas.repaint();
        }

        int i = 0, j = 0;
        int k = l;

        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                canvas.repaint();
                arr[k] = L[i];
                i++;

            } else {
                canvas.repaint();
                arr[k] = R[j];
                j++;

            }
            k++;
            canvas.repaint();

        }

        while (i < n1) {
            canvas.repaint();
            arr[k] = L[i];
            i++;
            k++;

        }

        while (j < n2) {
            canvas.repaint();
            arr[k] = R[j];
            j++;
            k++;

        }
        // canvas.repaint();
    }

    static void selectionSort(int[] arr, int n, Canvas canvas) {
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            int temp = arr[minIndex];
            arr[minIndex] = arr[i];
            arr[i] = temp;

            canvas.repaint();

            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
