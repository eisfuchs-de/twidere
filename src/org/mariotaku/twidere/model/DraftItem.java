/*
 *				Twidere - Twitter client for Android
 * 
 * Copyright (C) 2012 Mariotaku Lee <mariotaku.lee@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.mariotaku.twidere.model;

import org.mariotaku.twidere.provider.TweetStore.Drafts;
import org.mariotaku.twidere.util.ArrayUtils;

import android.database.Cursor;
import android.os.Parcelable;
import java.io.Serializable;
import android.os.Parcel;

public class DraftItem implements Serializable, Parcelable {

	public static final Parcelable.Creator<DraftItem> CREATOR = new Parcelable.Creator<DraftItem>() {
		@Override
		public DraftItem createFromParcel(final Parcel in) {
			return new DraftItem(in);
		}

		@Override
		public DraftItem[] newArray(final int size) {
			return new DraftItem[size];
		}
	};

	public final long[] account_ids;
	public final long _id, in_reply_to_status_id;
	public final String text, media_uri;
	public final boolean is_image_attached, is_photo_attached, is_possibly_sensitive;
	public final ParcelableLocation location;

	public DraftItem(final Cursor cursor, final int position) {
		cursor.moveToPosition(position);
		_id = cursor.getLong(cursor.getColumnIndex(Drafts._ID));
		text = cursor.getString(cursor.getColumnIndex(Drafts.TEXT));
		media_uri = cursor.getString(cursor.getColumnIndex(Drafts.IMAGE_URI));
		account_ids = ArrayUtils.fromString(cursor.getString(cursor.getColumnIndex(Drafts.ACCOUNT_IDS)), ',');
		in_reply_to_status_id = cursor.getLong(cursor.getColumnIndex(Drafts.IN_REPLY_TO_STATUS_ID));
		is_image_attached = cursor.getShort(cursor.getColumnIndex(Drafts.IS_IMAGE_ATTACHED)) == 1;
		is_photo_attached = cursor.getShort(cursor.getColumnIndex(Drafts.IS_PHOTO_ATTACHED)) == 1;
		is_possibly_sensitive = cursor.getShort(cursor.getColumnIndex(Drafts.IS_POSSIBLY_SENSITIVE)) == 1;
		location = new ParcelableLocation(cursor.getString(cursor.getColumnIndex(Drafts.LOCATION)));
	}

	public DraftItem(final Parcel in) {
		account_ids = in.createLongArray();
		_id = in.readLong();
		in_reply_to_status_id = in.readLong();
		text = in.readString();
		media_uri = in.readString();
		is_image_attached = in.readInt() == 1;
		is_photo_attached = in.readInt() == 1;
		is_possibly_sensitive = in.readInt() == 1;
		location = ParcelableLocation.fromString(in.readString());
	}

	public int describeContents() {
		return 0;
	}

	public void writeToParcel(final Parcel out, final int flags) {
		out.writeLongArray(account_ids);
		out.writeLong(_id);
		out.writeLong(in_reply_to_status_id);
		out.writeString(text);
		out.writeString(media_uri);
		out.writeInt(is_image_attached ? 1 : 0);
		out.writeInt(is_photo_attached ? 1 : 0);
		out.writeInt(is_possibly_sensitive ? 1 : 0);
		out.writeString(ParcelableLocation.toString(location));
	}
}
