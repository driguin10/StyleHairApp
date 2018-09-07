package com.stylehair.nerdsolutions.stylehair.auxiliar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;


public class Image {
	private String mime;
	private Bitmap bitmap;

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public void setMimeFromImgPath(String imgPath) {
		String[] aux = imgPath.split("\\.");
		this.mime = aux[aux.length - 1];
	}

	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public String getBitmapBase64(){
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		if(mime.equalsIgnoreCase("png"))
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		else
			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
		byte[] byteArray = stream.toByteArray();
		return(Base64.encodeBytes(byteArray));
	}
	

	public void setResizedBitmap(File file, int porcentagem) {
		try {
			bitmap = BitmapFactory.decodeFile(file.getPath());
			
			int w = bitmap.getWidth();
			int h = bitmap.getHeight();

			int scaleX = (w*porcentagem)/100;
			int scaleY = (h*porcentagem)/100;

			Matrix matrix = new Matrix();
			matrix.setScale(scaleX, scaleY);
			Bitmap auxBitmap = Bitmap.createScaledBitmap(bitmap, scaleX, scaleY, false);

			auxBitmap = fixMatrix(file, auxBitmap);
			bitmap.recycle();
			bitmap = auxBitmap;

		}
		catch (OutOfMemoryError e) { e.printStackTrace(); }
		catch (RuntimeException e) { e.printStackTrace(); }
		catch (IOException e) { e.printStackTrace(); }
	}
	
	private static Bitmap fixMatrix(File file, Bitmap bitmap) throws IOException {
		Matrix matrix = new Matrix();
		boolean fixed = false;
		ExifInterface exif = new ExifInterface(file.getPath()); // Classe para ler tags escritas no JPEG
		int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL); // Orienta��o que foi salva a foto

		// Rotate bitmap
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_180:
					matrix.postRotate(180);
					fixed = true;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					matrix.postRotate(90);
					fixed = true;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					matrix.postRotate(270);
					fixed = true;
					break;
				default:
					fixed = false;
					break;
			}

		if(!fixed) {

			return bitmap;
		}

			Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,true);
		
		bitmap.recycle();
		bitmap = null;

		return newBitmap;
	}


	public Uri getImageUri(Context inContext, Bitmap inImage) {
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
		String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
		return Uri.parse(path);
	}

	public String getRealPathFromURI(Context inContext,Uri uri) {
		Cursor cursor = inContext.getContentResolver().query(uri, null, null, null, null);
		cursor.moveToFirst();
		int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
		return cursor.getString(idx);
	}

	public static Bitmap drawableToBitmap (Drawable drawable) {
		Bitmap bitmap = null;

		if (drawable instanceof BitmapDrawable) {
			BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
			if(bitmapDrawable.getBitmap() != null) {
				return bitmapDrawable.getBitmap();
			}
		}

		if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
			bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
		} else {
			bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		}

		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}
}
