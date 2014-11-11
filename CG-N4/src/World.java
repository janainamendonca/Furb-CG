import java.util.ArrayList;
import java.util.List;

/**
 * Representa o mundo, como os polígonos já desenhados.
 * 
 * @author Janaina C Mendonça Lima
 * 
 */
public class World {

	private List<Polygon> polygons;
	private Camera camera;
	private Polygon selectedPolygon;

	public World() {
		polygons = new ArrayList<Polygon>();
	}

	public void setCamera() {
		camera = new Camera(-250.0f, 250.0f, -250.0f, 250.0f);
	}

	/**
	 * Remove o polígono selecionado e seus filhos
	 */
	public void removePolygon() {
		if (getSelectedPolygon() != null) {

			if (polygons.contains(selectedPolygon)) {
				polygons.remove(selectedPolygon);
			} else {
				if (selectedPolygon.getParent() != null) {
					selectedPolygon.getParent().getPolygons()
							.remove(selectedPolygon);
				}
			}
			selectedPolygon = null;
		}
	}

	public Camera getCamera() {
		return camera;
	}

	public List<Polygon> getPolygons() {
		return polygons;
	}

	public void addPolygon(Polygon polygon) {
		this.polygons.add(polygon);
	}

	public Polygon getSelectedPolygon() {
		return selectedPolygon;
	}

	public void setSelectedPolygon(Polygon polygon) {
		deselectPolygon();
		this.selectedPolygon = polygon;
		polygon.setSelected(true);
	}

	public void deselectPolygon() {
		if (this.selectedPolygon != null) {
			selectedPolygon.setSelected(false);
		}
		this.selectedPolygon = null;
	}

	private Polygon getSelectedBbox(Point selectedPoint, List<Polygon> polygons) {
		for (Polygon polygon : polygons) {
			BBox bbox = polygon.getBbox();
			if (/*   */selectedPoint.getX() <= bbox.getXMax()
					&& selectedPoint.getX() >= bbox.getXMin()//
					&& selectedPoint.getY() <= bbox.getYMax()
					&& selectedPoint.getY() >= bbox.getYMin()) {

				return polygon;
			} else {
				Polygon selectedBbox = getSelectedBbox(selectedPoint,
						polygon.getPolygons());
				if (selectedBbox != null) {
					return selectedBbox;
				}
			}
		}
		return null;
	}

	/**
	 * Verifica se o ponto informado corresponde a área de seleção de um
	 * polígono, se sim, seleciona o polígono e o retorna.
	 * 
	 * @param selectedPoint
	 *            pont a ser verificado
	 * @return o poligono selecionado, se houver
	 */
	public Polygon selectPolygon(Point selectedPoint) {

		Polygon selectedPolygon = getSelectedBbox(selectedPoint, polygons);

		if (selectedPolygon != null) {

			int numInt = 0;

			List<Point> points = selectedPolygon.getPoints();
			for (int i = 0; i < points.size(); i++) {
				Point p1 = points.get(i);
				Point p2 = i + 1 == points.size() ? points.get(0) : points
						.get(i + 1);

				double t = (selectedPoint.getY() - p1.getY())
						/ (p2.getY() - p1.getY());

				double xInt = p1.getX() + ((p2.getX() - p1.getX()) * t);
				double yInt = p1.getY() + ((p2.getY() - p1.getY()) * t);

				if (p1.getY() != p2.getY()) {

					if (t >= 0 && t <= 1) {

						if (p1.getX() == selectedPoint.getX()) {
							setSelectedPolygon(selectedPolygon);
							return selectedPolygon;
						}

						if (xInt > selectedPoint.getX() /**/
						// && yInt > Math.min(p1.getY(), p2.getY()) /**/
						// && yInt <= Math.max(p1.getY(), p2.getY())
						) {
							numInt++;
						}
					}

				} else if (selectedPoint.getY() == yInt/**/
						&& selectedPoint.getX() >= Math.min(p1.getX(),
								p2.getX())/**/
						&& selectedPoint.getX() <= Math.max(p1.getX(),
								p2.getX())) {
					setSelectedPolygon(selectedPolygon);
					return selectedPolygon;
				}
			}

			if (numInt % 2 != 0) {
				setSelectedPolygon(selectedPolygon);
			}

		}

		return selectedPolygon;
	}

}
