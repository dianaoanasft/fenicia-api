package com.fiapi.facade;

import com.fiapi.converter.DtoConverter;
import com.fiapi.converter.ModelConverter;
import com.fiapi.dto.RegionDto;
import com.fiapi.exception.ConversionException;
import com.fiapi.exception.ModelSaveException;
import com.fiapi.facade.impl.DefaultRegionFacade;
import com.fiapi.model.RegionModel;
import com.fiapi.service.I18NService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DefaultRegionFacadeTest {

    @Mock
    private I18NService i18NService;

    @Mock
    private ModelConverter<RegionDto, RegionModel> regionModelConverter;

    @Mock
    private DtoConverter<RegionModel, RegionDto> regionDtoConverter;

    @InjectMocks
    private DefaultRegionFacade regionFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSaveRegionSuccessfully() throws ConversionException, ModelSaveException {
        RegionDto regionDto = new RegionDto();
        RegionModel regionModel = new RegionModel();

        when(regionModelConverter.convert(regionDto)).thenReturn(regionModel);
        doNothing().when(i18NService).saveRegion(regionModel);

        regionFacade.saveRegion(regionDto);

        verify(regionModelConverter, times(1)).convert(regionDto);
        verify(i18NService, times(1)).saveRegion(regionModel);
    }

    @Test
    void shouldThrowException_whenSaveRegionFails() throws ConversionException, ModelSaveException {
        RegionDto regionDto = new RegionDto();
        RegionModel regionModel = new RegionModel();

        when(regionModelConverter.convert(regionDto)).thenReturn(regionModel);
        doThrow(new ModelSaveException("Failed to save region")).when(i18NService).saveRegion(regionModel);

        assertThatThrownBy(() -> regionFacade.saveRegion(regionDto))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to save region");

        verify(regionModelConverter, times(1)).convert(regionDto);
        verify(i18NService, times(1)).saveRegion(regionModel);
    }

    @Test
    void shouldGetRegionByCodeSuccessfully() throws ConversionException {
        String code = "regionCode";
        RegionModel regionModel = new RegionModel();
        RegionDto regionDto = new RegionDto();

        when(i18NService.fetchRegionByCode(code)).thenReturn(Optional.of(regionModel));
        when(regionDtoConverter.convert(regionModel)).thenReturn(regionDto);

        Optional<RegionDto> result = regionFacade.getRegionByCode(code);

        assertThat(result).isPresent().contains(regionDto);
        verify(i18NService, times(1)).fetchRegionByCode(code);
        verify(regionDtoConverter, times(1)).convert(regionModel);
    }

    @Test
    void shouldReturnEmptyOptional_whenRegionNotFound() throws ConversionException {
        String code = "regionCode";

        when(i18NService.fetchRegionByCode(code)).thenReturn(Optional.empty());

        Optional<RegionDto> result = regionFacade.getRegionByCode(code);

        assertThat(result).isEmpty();
        verify(i18NService, times(1)).fetchRegionByCode(code);
        verify(regionDtoConverter, never()).convert(any(RegionModel.class));
    }

    @Test
    void shouldDeleteRegionSuccessfully() throws ModelSaveException {
        String code = "regionCode";

        doNothing().when(i18NService).deleteRegion(code);

        regionFacade.deleteRegion(code);

        verify(i18NService, times(1)).deleteRegion(code);
    }

    @Test
    void shouldThrowException_whenDeleteRegionFails() throws ModelSaveException {
        String code = "regionCode";

        doThrow(new ModelSaveException("Failed to delete region")).when(i18NService).deleteRegion(code);

        assertThatThrownBy(() -> regionFacade.deleteRegion(code))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Failed to delete region");

        verify(i18NService, times(1)).deleteRegion(code);
    }

    @Test
    void shouldUpdateRegionSuccessfully() throws ConversionException, ModelSaveException {
        RegionDto regionDto = new RegionDto();
        regionDto.setCode("regionCode");
        RegionModel regionModel = new RegionModel();

        when(i18NService.fetchRegionByCode(regionDto.getCode())).thenReturn(Optional.of(regionModel));
        doNothing().when(i18NService).saveRegion(regionModel);

        regionFacade.updateRegion(regionDto);

        verify(i18NService, times(1)).fetchRegionByCode(regionDto.getCode());
        verify(i18NService, times(1)).saveRegion(regionModel);
    }

    @Test
    void shouldThrowException_whenUpdateRegionFails() throws ConversionException, ModelSaveException {
        RegionDto regionDto = new RegionDto();
        regionDto.setCode("regionCode");

        when(i18NService.fetchRegionByCode(regionDto.getCode())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> regionFacade.updateRegion(regionDto))
                .isInstanceOf(ModelSaveException.class)
                .hasMessageContaining("Region not found");

        verify(i18NService, times(1)).fetchRegionByCode(regionDto.getCode());
        verify(i18NService, never()).saveRegion(any(RegionModel.class));
    }

    @Test
    void shouldGetRegionByNameSuccessfully() throws ConversionException {
        String name = "regionName";
        RegionModel regionModel = new RegionModel();
        RegionDto regionDto = new RegionDto();

        when(i18NService.fetchRegionByName(name)).thenReturn(Optional.of(regionModel));
        when(regionDtoConverter.convert(regionModel)).thenReturn(regionDto);

        Optional<RegionDto> result = regionFacade.getRegionByName(name);

        assertThat(result).isPresent().contains(regionDto);
        verify(i18NService, times(1)).fetchRegionByName(name);
        verify(regionDtoConverter, times(1)).convert(regionModel);
    }

    @Test
    void shouldReturnEmptyOptional_whenRegionByNameNotFound() throws ConversionException {
        String name = "regionName";

        when(i18NService.fetchRegionByName(name)).thenReturn(Optional.empty());

        Optional<RegionDto> result = regionFacade.getRegionByName(name);

        assertThat(result).isEmpty();
        verify(i18NService, times(1)).fetchRegionByName(name);
        verify(regionDtoConverter, never()).convert(any(RegionModel.class));
    }

    @Test
    void shouldGetRegionsByCountrySuccessfully() throws ConversionException {
        String country = "countryName";
        RegionModel regionModel = new RegionModel();
        RegionDto regionDto = new RegionDto();
        List<RegionModel> regionModels = List.of(regionModel);
        List<RegionDto> regionDtos = List.of(regionDto);

        when(i18NService.fetchRegionsByCountry(country)).thenReturn(regionModels);
        when(regionDtoConverter.convert(regionModel)).thenReturn(regionDto);

        List<RegionDto> result = regionFacade.getRegionsByCountry(country);

        assertThat(result).isEqualTo(regionDtos);
        verify(i18NService, times(1)).fetchRegionsByCountry(country);
        verify(regionDtoConverter, times(1)).convert(regionModel);
    }

    @Test
    void shouldGetRegionByIsoCodeSuccessfully() throws ConversionException {
        String isoCode = "isoCode";
        RegionModel regionModel = new RegionModel();
        RegionDto regionDto = new RegionDto();

        when(i18NService.fetchRegionByIsoCode(isoCode)).thenReturn(Optional.of(regionModel));
        when(regionDtoConverter.convert(regionModel)).thenReturn(regionDto);

        Optional<RegionDto> result = regionFacade.getRegionByIsoCode(isoCode);

        assertThat(result).isPresent().contains(regionDto);
        verify(i18NService, times(1)).fetchRegionByIsoCode(isoCode);
        verify(regionDtoConverter, times(1)).convert(regionModel);
    }

    @Test
    void shouldReturnEmptyOptional_whenRegionByIsoCodeNotFound() throws ConversionException {
        String isoCode = "isoCode";

        when(i18NService.fetchRegionByIsoCode(isoCode)).thenReturn(Optional.empty());

        Optional<RegionDto> result = regionFacade.getRegionByIsoCode(isoCode);

        assertThat(result).isEmpty();
        verify(i18NService, times(1)).fetchRegionByIsoCode(isoCode);
        verify(regionDtoConverter, never()).convert(any(RegionModel.class));
    }
}