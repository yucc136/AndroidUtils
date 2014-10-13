package com.yucc.utils.picasso;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.squareup.picasso.Transformation;

public class MatrixTransformation implements Transformation {
	private float xScale ;
	private float yScale ;
	
	public MatrixTransformation(float xScale, float yScale) {
		super();
		this.xScale = xScale;
		this.yScale = yScale;
	}

	@Override
	public String key() {
		return "martrix()";
	}

	@Override
	public Bitmap transform(Bitmap source) {
		Matrix matrix = new Matrix();
		matrix.postScale(xScale, yScale); // 长和宽放大缩小的比例
		Bitmap resizeBmp = Bitmap.createBitmap(source,
				0, 0, source.getWidth(),
				source.getHeight(), matrix, true);
		if (resizeBmp != source) {
			source.recycle();
		}
		return resizeBmp;
	}

}
