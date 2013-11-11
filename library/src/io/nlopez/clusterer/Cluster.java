package io.nlopez.clusterer;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nacho Lopez on 28/10/13.
 */
public class Cluster<T extends Clusterable> implements Clusterable {

    private static final double BOUNDS_OFFSET = 0.1;

    private List<T> markers = new ArrayList<T>();
    private LatLng center;
    private Double latitudeSum;
    private Double longitudeSum;

    public Cluster(T marker) {
        addMarker(marker);
    }

    public void addMarker(T marker) {
        markers.add(marker);
        if (center == null) {
            center = marker.getPosition();
            latitudeSum = center.latitude;
            longitudeSum = center.longitude;
        } else {
            latitudeSum += marker.getPosition().latitude;
            longitudeSum += marker.getPosition().longitude;
            center = new LatLng(latitudeSum / markers.size(), longitudeSum / markers.size());
        }
    }

    public List<T> getMarkers() {
        return markers;
    }

    public LatLngBounds getBounds() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (T marker : markers) {
            builder.include(marker.getPosition());
        }
        return builder.build();
    }

    public LatLng getCenter() {
        return center;
    }

    public boolean isCluster() {
        return getWeight() > 1;
    }

    public int getWeight() {
        return markers.size();
    }

    @Override
    public LatLng getPosition() {
        return getCenter();
    }
}
