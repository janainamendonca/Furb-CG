package jcmendonca.tetris;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLCapabilities;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.sun.opengl.util.Animator;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private Main renderer = new Main();
	private Animator animator;

	public Frame() {
		// Cria o frame.
		super("CG-N3_Trasnformacao");
		setBounds(50, 100, 400, 600);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());

		/* Cria um objeto GLCapabilities para especificar 
		 * o numero de bits por pixel para RGBA
		 */
		GLCapabilities glCaps = new GLCapabilities();
		glCaps.setRedBits(8);
		glCaps.setBlueBits(8);
		glCaps.setGreenBits(8);
		glCaps.setAlphaBits(8);

		/* Cria um canvas, adiciona ao frame e objeto "ouvinte" 
		 * para os eventos Gl, de mouse e teclado
		 */
		GLCanvas canvas = new GLCanvas(glCaps);
		add(canvas, BorderLayout.CENTER);
		canvas.addGLEventListener(renderer);
		canvas.addKeyListener(renderer);
		canvas.requestFocus();

		animator = new Animator(canvas);

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
					}
				}).start();
			}
		});

	}

	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setVisible(true);
		frame.animator.start();
		frame.renderer.iniciaJogo();
	}

}
