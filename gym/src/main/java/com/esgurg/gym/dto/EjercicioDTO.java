package com.esgurg.gym.dto;

import com.esgurg.gym.entity.Ejercicio;
import com.esgurg.gym.entity.enumeration.GrupoMuscularObjetivo;
import com.esgurg.gym.entity.enumeration.TipoEjercicio;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EjercicioDTO {
    private Long ejercicioId;
    private String nombreEjercicio;
    private String descripcion;
    private String videoUrl;
    private Float minTiempoDescanso;
    private Float maxTiempoDescanso;
    private int minNroSeries;
    private int maxNroSeries;
    private int minNroReps;
    private int maxNroReps;
    private TipoEjercicio tipoEjercicio;
    private GrupoMuscularObjetivo grupoMuscularObjetivo;

    public EjercicioDTO(Ejercicio ejercicio) {
        this.ejercicioId = ejercicio.getEjercicioId();
        this.nombreEjercicio = ejercicio.getNombreEjercicio();
        this.descripcion = ejercicio.getDescripcion();
        this.videoUrl = ejercicio.getVideoUrl();
        this.minTiempoDescanso = ejercicio.getMinTiempoDescanso();
        this.maxTiempoDescanso = ejercicio.getMaxTiempoDescanso();
        this.minNroSeries = ejercicio.getMinNroSeries();
        this.maxNroSeries = ejercicio.getMaxNroSeries();
        this.minNroReps = ejercicio.getMinNroReps();
        this.maxNroReps = ejercicio.getMaxNroReps();
        this.tipoEjercicio = ejercicio.getTipoEjercicio();
        this.grupoMuscularObjetivo = ejercicio.getGrupoMuscularObjetivo();
    }

    public Ejercicio setValuesTo(Ejercicio ej) {
        if (this.ejercicioId != null)
            ej.setEjercicioId(this.ejercicioId);
        if (this.nombreEjercicio != null)
            ej.setNombreEjercicio(this.nombreEjercicio);
        if (this.descripcion != null)
            ej.setDescripcion(this.descripcion);
        if (this.videoUrl != null)
            ej.setVideoUrl(this.videoUrl);
        if (this.minTiempoDescanso != 0)
            ej.setMinTiempoDescanso(this.minTiempoDescanso);
        if (this.maxTiempoDescanso != 0)
            ej.setMaxTiempoDescanso(this.maxTiempoDescanso);
        if (this.minNroSeries != 0)
            ej.setMinNroSeries(this.minNroSeries);
        if (this.maxNroSeries != 0)
            ej.setMaxNroSeries(this.maxNroSeries);
        if (this.minNroReps != 0)
            ej.setMinNroReps(this.minNroReps);
        if (this.maxNroReps != 0)
            ej.setMaxNroReps(this.maxNroReps);
        if (this.tipoEjercicio != null)
            ej.setTipoEjercicio(this.tipoEjercicio);
        if (this.grupoMuscularObjetivo != null)
            ej.setGrupoMuscularObjetivo(this.grupoMuscularObjetivo);
        return ej;
    }

    public Map<String, Object> getEssentialInfo() {
        return Map.ofEntries(
                Map.entry("ejercicioId", this.ejercicioId),
                Map.entry("nombreEjercicio", this.nombreEjercicio),
                Map.entry("descripcion", this.descripcion),
                Map.entry("videoUrl", this.videoUrl),
                Map.entry("minTiempoDescanso", this.minTiempoDescanso),
                Map.entry("maxTiempoDescanso", this.maxTiempoDescanso),
                Map.entry("minNroSeries", this.minNroSeries),
                Map.entry("maxNroSeries", this.maxNroSeries),
                Map.entry("minNroReps", this.minNroReps),
                Map.entry("maxNroReps", this.maxNroReps),
                Map.entry("tipoEjercicio", this.tipoEjercicio),
                Map.entry("grupoMuscularObjetivo", this.grupoMuscularObjetivo)
        );
    }
}


