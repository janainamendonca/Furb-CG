package jcmendonca.tetris;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.media.opengl.DebugGL;
import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;

import jcmendonca.common.Transform;

import com.sun.opengl.util.Animator;
import com.sun.opengl.util.BufferUtil;
import com.sun.opengl.util.texture.TextureData;

public class VisEduCG implements GLEventListener {
	
	private GL gl;
	private GLU glu;
	private GLAutoDrawable drawable;
	
	private Transform matrix1, matrix2, matrix3;
	
	private float cor[] = {1, 1, 1};
	private float xMax, xMin, yMax, yMin, zMax, zMin;
	
	//	@INFORMAR CAMINHO DAS IMAGENS DE TEXTURA
	private String[] texturas = new String[] {
 "madeira.jpg", // Textura Caixa de Madeira
			"logoGCG.jpg" // Textura Caixa de Metal
	};
	private IntBuffer idsTextura;
	private int widthImg, heightImg;
	private BufferedImage image;
	private TextureData td;
	private ByteBuffer buffer;
	
	public void gerarTexturas(GL gl) {
		
		//Gera identificadores de textura
		idsTextura = BufferUtil.newIntBuffer(texturas.length);
		gl.glGenTextures(texturas.length, idsTextura);
		idsTextura.rewind();
		
		for (int i = 0; i < texturas.length; i++) {
			
			//Carrega imagem da textura
			try {
				loadImage(texturas[i]);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Especifica a textura corrente usando seu identificador
			gl.glBindTexture(GL.GL_TEXTURE_2D, idsTextura.get(i));
			
			//Envio da textura para OpenGL
			gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, 3, widthImg, heightImg, 0, GL.GL_BGR, GL.GL_UNSIGNED_BYTE, buffer);
			
			//Define os filtros de magnifica��o e minifica��o
			gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MIN_FILTER,GL.GL_LINEAR);
			gl.glTexParameteri(GL.GL_TEXTURE_2D,GL.GL_TEXTURE_MAG_FILTER,GL.GL_LINEAR);
			
		}
		
	}
	
	private void loadImage(String fileName) throws Exception {
		
		// Tenta carregar o arquivo
		image = null;
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			throw new Exception("Erro na leitura do arquivo "+fileName);
		}
		
		// Obtem largura e altura
		widthImg  = image.getWidth();
		heightImg = image.getHeight();
		
		// Gera uma nova TextureData...
		td = new TextureData(0,0,false,image);
		// ...e obt�m um ByteBuffer a partir dela
		buffer = (ByteBuffer) td.getBuffer();
	}
	
	private void gerarCubo( boolean usarTextura ) {
		// Face frontal
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, 0, 1);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMax, yMax, zMax);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMin, yMax, zMax);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMin, yMin, zMax);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMax, yMin, zMax);
		gl.glEnd();

		// Face posterior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, 0, -1);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMax, yMax, zMin);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMin);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMin);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMin);
		gl.glEnd();

		// Face lateral esquerda
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(-1, 0, 0);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMax);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMin);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMin);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMax);
		gl.glEnd();

		// Face lateral direita
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(1, 0, 0);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMax, yMax, zMax);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMax);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMin);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMax, yMax, zMin);
		gl.glEnd();

		// Face superior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, 1, 0);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMin, yMax, zMin);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMin, yMax, zMax);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMax, yMax, zMax);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMax, yMax, zMin);
		gl.glEnd();

		// Face inferior
		gl.glBegin(GL.GL_QUADS);
		gl.glNormal3f(0, -1, 0);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(xMin, yMin, zMin);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(xMax, yMin, zMin);
		if ( usarTextura ) gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(xMax, yMin, zMax);
		if ( usarTextura ) gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(xMin, yMin, zMax);
		gl.glEnd();
	}
	
	public static void main(String[] args) {
		Frame frame = new Frame();
		frame.setTitle("VisEdu-CG");
		GLCanvas canvas = new GLCanvas();
		VisEduCG gear = new VisEduCG();
		canvas.addGLEventListener(gear);
		frame.add(canvas);
		frame.setSize(600, 600);
		final Animator animator = new Animator(canvas);
		
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				new Thread(new Runnable() {
					public void run() {
						animator.stop();
						System.exit(0);
					}
				}).start();
			}
		});
		
		frame.setVisible(true);
		animator.start();
	}
	
	public void init(GLAutoDrawable drawable) {
		this.drawable = drawable;
		gl = drawable.getGL();
		glu = new GLU();
		// Use debug pipeline
		drawable.setGL(new DebugGL(drawable.getGL()));
		
		System.err.println("INIT GL IS: " + gl.getClass().getName());
		
		System.err.println("Chosen GLCapabilities: "
			+ drawable.getChosenGLCapabilities());
		
		gl.setSwapInterval(1);
		
		gl.glEnable(GL.GL_CULL_FACE);
		
		gl.glEnable(GL.GL_DEPTH_TEST);
		
		gl.glEnable(GL.GL_LIGHT0);
		//gl.glEnable(GL.GL_LIGHT1);
		//gl.glEnable(GL.GL_LIGHTING);
		
		gl.glEnable(GL.GL_COLOR_MATERIAL);
		gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL.GL_AMBIENT_AND_DIFFUSE);
		
		gerarTexturas(gl);
		
		gl.glEnable(GL.GL_NORMALIZE);
		
		defineIluminacao();
		
		gl.glClearColor( 0f, 0f, 0f, 1.0f);
	}
	
	// Funcao responsavel pela especificacao dos parametros de iluminacao
	private void defineIluminacao()	{
		//Define os parametros atraves de vetores RGBA - o ultimo valor deve ser sempre 1.0f
		float luzAmbiente[]={0.2f, 0.2f, 10f, 1.0f};
		
		float luzDifusa[]={1.0f, 1.0f, 1.0f, 1.0f};
		float luzEspecular[]={1.0f, 1.0f, 1.0f, 1.0f};
		float posicaoLuz[]={0.0f, 0.0f, 10.0f, 1.0f}; // ultimo parametro: 0-direcional, 1-pontual/posicional
		
		//Ativa o uso da luz ambiente
		gl.glLightModelfv(GL.GL_LIGHT_MODEL_AMBIENT, luzAmbiente, 0);
		
		//Define os parametros da luz de numero 0
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_AMBIENT, luzAmbiente, 0);
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_DIFFUSE, luzDifusa, 0 );
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_SPECULAR, luzEspecular, 0);
		//gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posicaoLuz, 0 );
		
		/*float posicaoLuz2[]={0.0f, 0.0f, -10.0f, 1.0f};
		float luzEspecular2[]={1.0f, 1.0f, 1.0f, 0.0f};
		float luzDifusa2[]={1.0f, 1.0f, 1.0f, 1.0f};
		
		//Define os parametros da luz de numero 1
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_AMBIENT, luzAmbiente, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_DIFFUSE, luzDifusa2, 0 );
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_SPECULAR, luzEspecular2, 0);
		gl.glLightfv(GL.GL_LIGHT1, GL.GL_POSITION, posicaoLuz2, 0 );*/
		
		// Brilho do material
		float especularidade[]={1.0f, 1.0f, 1.0f, 1.0f};
		int especMaterial = 60;
		
		// Define a reflectancia do material
		gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, especularidade, 0);
		// Define a concentracao do brilho
		gl.glMateriali(GL.GL_FRONT, GL.GL_SHININESS, especMaterial);
	}
	
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		float h = (float) height / (float) width;
		
		gl.glMatrixMode(GL.GL_PROJECTION);
		gl.glLoadIdentity();
		
		//gl.glFrustum(-10.0f, 10.0f, -h, h, 5.0f, 60.0f);
		glu.gluPerspective( 45 , h, 100, 600);
		
		gl.glViewport(0, 0, width, height);
		
		gl.glMatrixMode(GL.GL_MODELVIEW);
		gl.glLoadIdentity();
		
		float posicaoCamera[] = { 300, 300, 300 };
		float lookAtCamera[] = { 0, 0, 0 };
		glu.gluLookAt( posicaoCamera[0], posicaoCamera[1], posicaoCamera[2],
			lookAtCamera[0], lookAtCamera[1], lookAtCamera[2], 0, 1, 0);
		
		gl.glLightfv(GL.GL_LIGHT0, GL.GL_POSITION, posicaoCamera, -1);
	}
	
	public void display(GLAutoDrawable drawable) {
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT );
		
			//Objeto Gráfico 1
			gl.glPushMatrix();

			//Cubo 1
			gl.glShadeModel(GL.GL_FLAT);
			gl.glNormal3f(0.0f, 0.0f, 1.0f);
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cor, 0);
			gl.glColor3f( 1f, 1f, 1f );

			xMax = 50f;
			xMin = -50f;
			yMax = 50f;
			yMin = -50f;
			zMax = 50f;
			zMin = -50f;

			gl.glBindTexture(GL.GL_TEXTURE_2D, idsTextura.get( 0 )); //Posiciona na Textura Caixa de Madeira
			gl.glEnable(GL.GL_TEXTURE_2D);	// Habilita uso de textura

			gerarCubo( true );

			gl.glDisable(GL.GL_TEXTURE_2D);	//	Desabilita uso de textura

			gl.glPopMatrix();

			//Objeto Gráfico 2
			gl.glPushMatrix();

			//Transladar 1
			matrix1 = new Transform();
			matrix1.makeTranslation( 100f, 0f, 0f );
			gl.glMultMatrixd(matrix1.getDate(), 0);

			//Cubo 2
			gl.glShadeModel(GL.GL_FLAT);
			gl.glNormal3f(0.0f, 0.0f, 1.0f);
			gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT_AND_DIFFUSE, cor, 0);
			gl.glColor3f( 1f, 1f, 1f );

			xMax = 50f;
			xMin = -50f;
			yMax = 50f;
			yMin = -50f;
			zMax = 50f;
			zMin = -50f;

			gl.glBindTexture(GL.GL_TEXTURE_2D, idsTextura.get( 1 )); //Posiciona na Textura Caixa de Metal
			gl.glEnable(GL.GL_TEXTURE_2D);	// Habilita uso de textura

			gerarCubo( true );

			gl.glDisable(GL.GL_TEXTURE_2D);	//	Desabilita uso de textura

			gl.glPopMatrix();

		
		gl.glFlush();
		
	}
	
	public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
	}
	
	// Internal matrix element organization reference
	//             [ matrix[0] matrix[4] matrix[8]  matrix[12] ]
	//    Matrix = [ matrix[1] matrix[5] matrix[9]  matrix[13] ]
	//             [ matrix[2] matrix[6] matrix[10] matrix[14] ]
	//             [ matrix[3] matrix[7] matrix[11] matrix[15] ]

}