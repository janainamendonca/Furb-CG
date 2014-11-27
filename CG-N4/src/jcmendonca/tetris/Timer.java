package jcmendonca.tetris;

public class Timer {

	/**
	 * O número de milissegundos por ciclo
	 */
	private float millisPerCycle;

	private long lastUpdate;

	/**
	 * O numero de cilos que se passaram e ainda não foram consultados
	 */
	private int elapsedCycles;

	/**
	 * A quantidade de excesso de tempo para o próximo ciclo decorrido
	 */
	private float excessCycles;

	/**
	 * Indica se este timer está ou nao pausado
	 */
	private boolean isPaused;

	public Timer(float cyclesPerSecond) {
		setCyclesPerSecond(cyclesPerSecond);
		reset();
	}

	public void setCyclesPerSecond(float cyclesPerSecond) {
		this.millisPerCycle = (1.0f / cyclesPerSecond) * 1000;
	}

	public void reset() {
		this.elapsedCycles = 0;
		this.excessCycles = 0.0f;
		this.lastUpdate = getCurrentTime();
		this.isPaused = false;
	}

	public void update() {
		long currUpdate = getCurrentTime();
		float delta = (float) (currUpdate - lastUpdate) + excessCycles;

		//atualiza o numero de cliclos decorridos se o jogo não estiver pausado.
		if (!isPaused) {
			this.elapsedCycles += (int) Math.floor(delta / millisPerCycle);
			this.excessCycles = delta % millisPerCycle;
		}

		this.lastUpdate = currUpdate;
	}

	public void setPaused(boolean paused) {
		this.isPaused = paused;
	}

	public boolean isPaused() {
		return isPaused;
	}

	public boolean hasElapsedCycle() {
		if (elapsedCycles > 0) {
			this.elapsedCycles--;
			return true;
		}
		return false;
	}

	private static final long getCurrentTime() {
		return (System.nanoTime() / 1000000L);
	}

}
