package com.jjoe64.graphview;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.jjoe64.graphview.compatible.ScaleGestureDetector;

/**
 * GraphView is a Android View for creating zoomable and scrollable graphs.
 * This is the abstract base class for all graphs. Extend this class and implement {@link #drawSeries(Canvas, GraphViewData[], float, float, float, double, double, double, double, float)} to display a custom graph.
 * Use {@link LineGraphView} for creating a line chart.
 *
 * @author jjoe64 - jonas gehring - http://www.jjoe64.com
 *
 * Copyright (C) 2011 Jonas Gehring
 * Licensed under the GNU Lesser General Public License (LGPL)
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * --------------------------------------------------------------------------------
 * 
 * @author Lamine Sissoko			(display changes)
 * 
 */
abstract public class GraphView extends LinearLayout {
	
	final float MIN_FONT_DIPS = 10.0f; //14.0f;
	final float scale = getResources().getDisplayMetrics().density;
	private int defaultFontHt = (int)(MIN_FONT_DIPS * scale + 0.5f);
	private int paintColor = android.graphics.Color.BLACK;
	private boolean dayHighlight = false;
	
	public void setDayHighlight(boolean b){
		dayHighlight = b;
	}
	
	
	static final private class GraphViewConfig {
		static final float BORDER = 20;
		static final float VERTICAL_LABEL_WIDTH = 100;
		static final float HORIZONTAL_LABEL_HEIGHT = 80;
	}

	private class GraphViewContentView extends View {
		private float lastTouchEventX;
		private float graphwidth;

		/**
		 * @param context
		 */
		public GraphViewContentView(Context context) {
			super(context);
			setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		}

		/**
		 * @param canvas
		 */
		@Override
		protected void onDraw(Canvas canvas) {

            paint.setAntiAlias(true);

			// normal
			paint.setStrokeWidth(0);

			float border = GraphViewConfig.BORDER;
			float horstart = 0;
			float height = getHeight() - 20; //////////
			float width = getWidth() - 1;
			double maxY = getMaxY();
			double minY = getMinY();
			double diffY = maxY - minY;
			double maxX = getMaxX(false);
			double minX = getMinX(false);
			double diffX = maxX - minX;
			float graphheight = height - (2 * border);
			graphwidth = width;

			if (horlabels == null) {
				horlabels = generateHorlabels(graphwidth);
			}
			if (verlabels == null) {
				verlabels = generateVerlabels(graphheight);
			}

			// vertical lines
			paint.setTextAlign(Align.LEFT);
			int vers = verlabels.length - 1;
			for (int i = 0; i < verlabels.length; i++) {
				paint.setColor(Color.DKGRAY);
				float y = ((graphheight / vers) * i) + border;
				canvas.drawLine(horstart, y, width, y, paint);
			}

			// horizontal labels + lines
			int hors = horlabels.length - 1;
			for (int i = 0; i < horlabels.length; i++) {
				paint.setColor(Color.DKGRAY);
				float x = ((graphwidth / hors) * i) + horstart;
				canvas.drawLine(x, height - border, x, border, paint);
				paint.setTextAlign(Align.CENTER);
				if (i==horlabels.length-1)
					paint.setTextAlign(Align.RIGHT);
				if (i==0)
					paint.setTextAlign(Align.LEFT);
				//paint.setColor(Color.WHITE);
				paint.setColor(paintColor);
				if(dayHighlight && i == new java.util.Date().getDay())
					paint.setColor(Color.RED);
				paint.setTextSize(defaultFontHt);
				canvas.drawText(horlabels[i], x, height - 4, paint);
			}
			
			// X axis title
			//canvas.drawText("Days", (graphwidth / 2) + horstart, getHeight() - 4, paint);

			paint.setTextAlign(Align.CENTER);
			paint.setColor(paintColor);
			paint.setTextSize(defaultFontHt * 1.2f);
			canvas.drawText(title, (graphwidth / 2) + horstart, border - 4, paint);
			//canvas.drawText(title, border, graphheight, paint);

			if (maxY != minY) {
				paint.setStrokeCap(Paint.Cap.ROUND);

				for (int i=0; i<graphSeries.size(); i++) {
					paint.setStrokeWidth(graphSeries.get(i).style.thickness);
					paint.setColor(graphSeries.get(i).style.color);
					drawSeries(canvas, _values(i), graphwidth, graphheight, border, minX, minY, diffX, diffY, horstart);
				}

				if (showLegend) drawLegend(canvas, height, width);
			}
		}

		private void onMoveGesture(float f) {
			// view port update
			if (viewportSize != 0) {
				viewportStart -= f*viewportSize/graphwidth;

				// minimal and maximal view limit
				double minX = getMinX(true);
				double maxX = getMaxX(true);
				if (viewportStart < minX) {
					viewportStart = minX;
				} else if (viewportStart+viewportSize > maxX) {
					viewportStart = maxX - viewportSize;
				}

				// labels have to be regenerated
				horlabels = null;
				verlabels = null;
				viewVerLabels.invalidate();
			}
			invalidate();
		}

		/**
		 * @param event
		 */
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			if (!isScrollable()) {
				return super.onTouchEvent(event);
			}

			boolean handled = false;
			// first scale
			if (scalable && scaleDetector != null) {
				scaleDetector.onTouchEvent(event);
				handled = scaleDetector.isInProgress();
			}
			if (!handled) {
				// if not scaled, scroll
				if ((event.getAction() & MotionEvent.ACTION_DOWN) == MotionEvent.ACTION_DOWN) {
					handled = true;
				}
				if ((event.getAction() & MotionEvent.ACTION_UP) == MotionEvent.ACTION_UP) {
					lastTouchEventX = 0;
					handled = true;
				}
				if ((event.getAction() & MotionEvent.ACTION_MOVE) == MotionEvent.ACTION_MOVE) {
					if (lastTouchEventX != 0) {
						onMoveGesture(event.getX() - lastTouchEventX);
					}
					lastTouchEventX = event.getX();
					handled = true;
				}
				if (handled)
					invalidate();
			}
			return handled;
		}
	}

	/**
	 * graph series style: color and thickness
	 */
	static public class GraphViewStyle {
		public int color = 0xff0077cc;
		public int thickness = 3;
		public GraphViewStyle() {
			super();
		}
		public GraphViewStyle(int color, int thickness) {
			super();
			this.color = color;
			this.thickness = thickness;
		}
	}

	/**
	 * one data set for a graph series
	 */
	static public class GraphViewData {
		public final double valueX;
		public final double valueY;
		public GraphViewData(double valueX, double valueY) {
			super();
			this.valueX = valueX;
			this.valueY = valueY;
		}
	}

	/**
	 * a graph series
	 */
	static public class GraphViewSeries {
		final String description;
		final GraphViewStyle style;
		final GraphViewData[] values;
		public GraphViewSeries(GraphViewData[] values) {
			description = null;
			style = new GraphViewStyle();
			this.values = values;
		}
		public GraphViewSeries(String description, GraphViewStyle style, GraphViewData[] values) {
			super();
			this.description = description;
			if (style == null) {
				style = new GraphViewStyle();
			}
			this.style = style;
			this.values = values;
		}
	}

	public enum LegendAlign {
		TOP, MIDDLE, BOTTOM
	}

	private class VerLabelsView extends View {
		/**
		 * @param context
		 */
		public VerLabelsView(Context context) {
			super(context);
			setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 10));
		}

		/**
		 * @param canvas
		 */
		@Override
		protected void onDraw(Canvas canvas) {
			// normal
			paint.setStrokeWidth(0);

			float border = GraphViewConfig.BORDER;
			float height = getHeight() - 20; ///////////////////////////
			float graphheight = height - (2 * border);

			if (verlabels == null) {
				verlabels = generateVerlabels(graphheight);
			}

			// vertical labels
			paint.setTextAlign(Align.LEFT);
			int vers = verlabels.length - 1;
			for (int i = 0; i < verlabels.length; i++) {
				float y = ((graphheight / vers) * i) + border;
				//paint.setColor(Color.WHITE);
				paint.setColor(paintColor);
				paint.setTextSize(defaultFontHt);
				//canvas.drawText(verlabels[i], 0, y, paint);
				canvas.drawText(verlabels[i], border - 12, y, paint);
			}
		}
	}

	protected final Paint paint;
	private String[] horlabels;
	private String[] verlabels;
	private String title;
	private boolean scrollable;
	private double viewportStart;
	private double viewportSize;
	private final View viewVerLabels;
	private ScaleGestureDetector scaleDetector;
	private boolean scalable;
	private NumberFormat numberformatter;
	private final List<GraphViewSeries> graphSeries;
	private boolean showLegend = false;
	private float legendWidth = 120;
	private LegendAlign legendAlign = LegendAlign.MIDDLE;
	private boolean manualYAxis;
	private double manualMaxYValue;
	private double manualMinYValue;

	/**
	 *
	 * @param context
	 * @param title [optional]
	 */
	public GraphView(Context context, String title) {
		super(context);
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

		if (title == null)
			title = "";
		else
			this.title = title;

		paint = new Paint();
		graphSeries = new ArrayList<GraphViewSeries>();

		viewVerLabels = new VerLabelsView(context);
		addView(viewVerLabels);
		addView(new GraphViewContentView(context), new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, 1));
	}

	private GraphViewData[] _values(int idxSeries) {
		GraphViewData[] values = graphSeries.get(idxSeries).values;
		if (viewportStart == 0 && viewportSize == 0) {
			// all data
			return values;
		} else {
			// viewport
			List<GraphViewData> listData = new ArrayList<GraphViewData>();
			for (int i=0; i<values.length; i++) {
				if (values[i].valueX >= viewportStart) {
					if (values[i].valueX > viewportStart+viewportSize) {
						listData.add(values[i]); // one more for nice scrolling
						break;
					} else {
						listData.add(values[i]);
					}
				} else {
					if (listData.isEmpty()) {
						listData.add(values[i]);
					}
					listData.set(0, values[i]); // one before, for nice scrolling
				}
			}
			return listData.toArray(new GraphViewData[listData.size()]);
		}
	}

	public void addSeries(GraphViewSeries series) {
		graphSeries.add(series);
	}

	public void removeSeries(int index)
	{
		if (index < 0 || index >= graphSeries.size())
		{
			throw new IndexOutOfBoundsException("No series at index " + index);
		}
		
		graphSeries.remove(index);
	}
	
	public void removeSeries(GraphViewSeries series)
	{
		graphSeries.remove(series);
	}
	
	protected void drawLegend(Canvas canvas, float height, float width) {
		int shapeSize = 15;

		// rect
		paint.setARGB(180, 100, 100, 100);
		float legendHeight = (shapeSize+5)*graphSeries.size() +5;
		float lLeft = width-legendWidth - 10;
		float lTop;
		switch (legendAlign) {
		case TOP:
			lTop = 10 + 10; ////////
			break;
		case MIDDLE:
			lTop = height/2 - legendHeight/2;
			break;
		default:
			lTop = height - GraphViewConfig.BORDER - legendHeight -10;
		}
		float lRight = lLeft+legendWidth;
		float lBottom = lTop+legendHeight;
		paint.setColor(0xffeeeeee);
		paint.setAlpha(180);
		canvas.drawRoundRect(new RectF(lLeft, lTop, lRight, lBottom), 8, 8, paint);

		for (int i=0; i<graphSeries.size(); i++) {
			paint.setColor(graphSeries.get(i).style.color);
			canvas.drawRect(new RectF(lLeft+5, lTop+5+(i*(shapeSize+5)), lLeft+5+shapeSize, lTop+((i+1)*(shapeSize+5))), paint);
			if (graphSeries.get(i).description != null) {
				//paint.setColor(Color.WHITE);
				paint.setColor(paintColor);
				paint.setTextSize(defaultFontHt);
				paint.setTextAlign(Align.LEFT);
				canvas.drawText(graphSeries.get(i).description, lLeft+5+shapeSize+5, lTop+shapeSize+(i*(shapeSize+5)), paint);
			}
		}
	}

	abstract public void drawSeries(Canvas canvas, GraphViewData[] values, float graphwidth, float graphheight, float border, double minX, double minY, double diffX, double diffY, float horstart);

	/**
	 * formats the label
	 * can be overwritten
	 * @param value x and y values
	 * @param isValueX if false, value y wants to be formatted
	 * @return value to display
	 */
	protected String formatLabel(double value, boolean isValueX) {
		if (numberformatter == null) {
			numberformatter = NumberFormat.getNumberInstance();
			double highestvalue = getMaxY();
			double lowestvalue = getMinY();
			if (highestvalue - lowestvalue < 0.1) {
				numberformatter.setMaximumFractionDigits(6);
			} else if (highestvalue - lowestvalue < 1) {
				numberformatter.setMaximumFractionDigits(4);
			} else if (highestvalue - lowestvalue < 20) {
				numberformatter.setMaximumFractionDigits(3);
			} else if (highestvalue - lowestvalue < 100) {
				numberformatter.setMaximumFractionDigits(1);
			} else {
				numberformatter.setMaximumFractionDigits(0);
			}
		}
		return numberformatter.format(value);
	}

	private String[] generateHorlabels(float graphwidth) {
		int numLabels = (int) (graphwidth/GraphViewConfig.VERTICAL_LABEL_WIDTH);
		String[] labels = new String[numLabels+1];
		double min = getMinX(false);
		double max = getMaxX(false);
		for (int i=0; i<=numLabels; i++) {
			labels[i] = formatLabel(min + ((max-min)*i/numLabels), true);
		}
		return labels;
	}

	synchronized private String[] generateVerlabels(float graphheight) {
		int numLabels = (int) (graphheight/GraphViewConfig.HORIZONTAL_LABEL_HEIGHT);
		String[] labels = new String[numLabels+1];
		double min = getMinY();
		double max = getMaxY();
		for (int i=0; i<=numLabels; i++) {
			labels[numLabels-i] = formatLabel(min + ((max-min)*i/numLabels), false);
		}
		return labels;
	}

	public LegendAlign getLegendAlign() {
		return legendAlign;
	}

	public float getLegendWidth() {
		return legendWidth;
	}

	private double getMaxX(boolean ignoreViewport) {
		// if viewport is set, use this
		if (!ignoreViewport && viewportSize != 0) {
			return viewportStart+viewportSize;
		} else {
			// otherwise use the max x value
			// values must be sorted by x, so the last value has the largest X value
			double highest = 0;
			if (graphSeries.size() > 0)
			{
				GraphViewData[] values = graphSeries.get(0).values;
				highest = values[values.length-1].valueX;
				for (int i=1; i<graphSeries.size(); i++) {
					values = graphSeries.get(i).values;
					highest = Math.max(highest, values[values.length-1].valueX);
				}
			}
			return highest;
		}
	}

	private double getMaxY() {
		double largest;
		if (manualYAxis) {
			largest = manualMaxYValue;
		} else {
			largest = Integer.MIN_VALUE;
			for (int i=0; i<graphSeries.size(); i++) {
				GraphViewData[] values = _values(i);
				for (int ii=0; ii<values.length; ii++)
					if (values[ii].valueY > largest)
						largest = values[ii].valueY;
			}
		}
		return largest;
	}

	private double getMinX(boolean ignoreViewport) {
		// if viewport is set, use this
		if (!ignoreViewport && viewportSize != 0) {
			return viewportStart;
		} else {
			// otherwise use the min x value
			// values must be sorted by x, so the first value has the smallest X value
			double lowest = 0;
			if (graphSeries.size() > 0)
			{
				GraphViewData[] values = graphSeries.get(0).values;
				lowest = values[0].valueX;
				for (int i=1; i<graphSeries.size(); i++) {
					values = graphSeries.get(i).values;
					lowest = Math.min(lowest, values[0].valueX);
				}
			}
			return lowest;
		}
	}

	private double getMinY() {
		double smallest;
		if (manualYAxis) {
			smallest = manualMinYValue;
		} else {
			smallest = Integer.MAX_VALUE;
			for (int i=0; i<graphSeries.size(); i++) {
				GraphViewData[] values = _values(i);
				for (int ii=0; ii<values.length; ii++)
					if (values[ii].valueY < smallest)
						smallest = values[ii].valueY;
			}
		}
		return smallest;
	}

	public boolean isScrollable() {
		return scrollable;
	}

	public boolean isShowLegend() {
		return showLegend;
	}

	/**
	 * set's static horizontal labels (from left to right)
	 * @param horlabels if null, labels were generated automatically
	 */
	public void setHorizontalLabels(String[] horlabels) {
		this.horlabels = horlabels;
	}

	public void setLegendAlign(LegendAlign legendAlign) {
		this.legendAlign = legendAlign;
	}

	public void setLegendWidth(float legendWidth) {
		this.legendWidth = legendWidth;
	}

	/**
	 * you have to set the bounds {@link #setManualYAxisBounds(double, double)}. That automatically enables manualYAxis-flag.
	 * if you want to disable the menual y axis, call this method with false.
	 * @param manualYAxis
	 */
	public void setManualYAxis(boolean manualYAxis) {
		this.manualYAxis = manualYAxis;
	}

	/**
	 * set manual Y axis limit
	 * @param max
	 * @param min
	 */
	public void setManualYAxisBounds(double max, double min) {
		manualMaxYValue = max;
		manualMinYValue = min;
		manualYAxis = true;
	}

	/**
	 * this forces scrollable = true
	 * @param scalable
	 */
	synchronized public void setScalable(boolean scalable) {
		this.scalable = scalable;
		if (scalable == true && scaleDetector == null) {
			scrollable = true; // automatically forces this
			scaleDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.SimpleOnScaleGestureListener() {
				
				public boolean onScale(ScaleGestureDetector detector) {
					double center = viewportStart + viewportSize / 2;
					viewportSize /= detector.getScaleFactor();
					viewportStart = center - viewportSize / 2;
					
					// viewportStart must not be < minX
					double minX = getMinX(true);
					if (viewportStart < minX) {
						viewportStart = minX;
					}

					// viewportStart + viewportSize must not be > maxX
					double maxX = getMaxX(true);
					double overlap = viewportStart + viewportSize - maxX;
					if (overlap > 0) {
						// scroll left
						if (viewportStart-overlap > minX) {
							viewportStart -= overlap;
						} else {
							// maximal scale
							viewportStart = minX;
							viewportSize = maxX - viewportStart;
						}
					}

					verlabels = null;
					horlabels = null;
					numberformatter = null;
					invalidate();
					viewVerLabels.invalidate();
					return true;
				}
			});
		}
	}

	/**
	 * the user can scroll (horizontal) the graph. This is only useful if you use a viewport {@link #setViewPort(double, double)} which doesn't displays all data.
	 * @param scrollable
	 */
	public void setScrollable(boolean scrollable) {
		this.scrollable = scrollable;
	}

	public void setShowLegend(boolean showLegend) {
		this.showLegend = showLegend;
	}

	/**
	 * set's static vertical labels (from top to bottom)
	 * @param verlabels if null, labels were generated automatically
	 */
	public void setVerticalLabels(String[] verlabels) {
		this.verlabels = verlabels;
	}

	/**
	 * set's the viewport for the graph.
	 * @param start x-value
	 * @param size
	 */
	public void setViewPort(double start, double size) {
		viewportStart = start;
		viewportSize = size;
	}
}
