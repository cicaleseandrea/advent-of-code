package com.adventofcode.utils;


import static java.lang.Math.abs;

import java.util.List;

public record Point(int i, int j) {

  public Point add(int i, int j) {
    return new Point( this.i + i, this.j + j );
  }

  public Point add(Point point) {
    return add( point.i, point.j );
  }

  public Point move(Direction direction, int n) {
    return switch ( direction ) {
      case DOWN -> add( n, 0 );
      case UP -> add( -n, 0 );
      case RIGHT -> add( 0, n );
      case LEFT -> add( 0, -n );
    };
  }

  public Point move(Direction direction) {
    return move( direction, 1 );
  }

  public static double computeArea(List<Point> points) {
    //Shoelace formula
    long area = 0;
    for ( int i = 0; i < points.size(); i++ ) {
      final Point curr = points.get( i );
      final Point next = points.get( (i + 1) % points.size() );
      area += ((long) curr.i() * next.j()) - ((long) curr.j() * next.i());
    }
    return abs( area ) / 2.0;
  }
}
