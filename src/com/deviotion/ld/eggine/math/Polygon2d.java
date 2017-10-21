package com.deviotion.ld.eggine.math;

import java.util.List;

public class Polygon2d {
	private List<Vector2d> points;

	public Polygon2d(List<Vector2d> points) {
		this.points = points;
	}

	public boolean intersects(int x, int y) {
		for (int i = 0; i < points.size(); ++i) {
			Vector2d current = points.get(i);
			Vector2d next = i == (points.size() - 1) ? points.get(0) : points.get(i + 1);

			Vector2d dir = next.subtract(current);
			Vector2d normal = new Vector2d(-dir.getY(), dir.getX());

			Vector2d current2Point = new Vector2d(x, y).subtract(current);

			if (current2Point.dot(normal) < 0)
				return false;
		}

		return true;
	}

	public List<Vector2d> getPoints() {
		return points;
	}
}
