package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.dto.AdsDTO;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.dto.ExtendedAdDTO;
import ru.skypro.homework.dto.mapper.AdMapper;
import ru.skypro.homework.model.AdModel;
import ru.skypro.homework.model.UserModel;
import ru.skypro.homework.service.AdService;
import ru.skypro.homework.service.UserService;

import java.io.IOException;
import java.util.List;

public class AddController {

    private final AdService adService;
    private final UserService userService;
    private final AdMapper adMapper;
    public AddController(AdService adService, UserService userService, AdMapper adMapper) {
        this.adService = adService;
        this.userService = userService;
        this.adMapper = adMapper;
    }

    @Operation(
            summary = "Получение всех объявлений",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AdsDTO.class)
                                    )
                            }
                    )
            }, tags = "Объявления"
    )
    @GetMapping
    public ResponseEntity<AdsDTO> getAllAds() {
        AdsDTO result = adMapper.mapListAdModelToAdsDTO(adService.getAllAds());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @Operation(
            summary = "Добавление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Created",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AdDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }, tags = "Объявления"
    )
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AdDTO> addAd(@RequestPart("properties") CreateOrUpdateAdDTO properties,
                                       Authentication authentication) throws IOException {

        AdModel adModel = adService.createAd(authentication.getName(), properties);
        AdDTO adDTO = adMapper.mapAdModelToAdDTO(adModel);
        return new ResponseEntity<>(adDTO, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Получение информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = ExtendedAdDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }, tags = "Объявления"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ExtendedAdDTO> getAds(@PathVariable int id) {
        AdModel adModel = adService.findAdById(id);
        ExtendedAdDTO extendedAdDTO = adMapper.mapAdModelToExtendedAdDTO(adModel);
        return new ResponseEntity<>(extendedAdDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Удаление объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No Content"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }, tags = "Объявления"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeAd(@PathVariable int id, Authentication authentication) {

        adService.deleteAd(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Обновление информации об объявлении",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AdDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }, tags = "Объявления"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<AdDTO> updateAds(@PathVariable int id, @RequestBody CreateOrUpdateAdDTO properties,
                                           Authentication authentication) {

        AdModel updatedAd = adService.updateAd(id, properties);
        AdDTO adDTO = adMapper.mapAdModelToAdDTO(updatedAd);
        return new ResponseEntity<>(adDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Получение объявлений авторизованного пользователя",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = AdsDTO.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }, tags = "Объявления"
    )
    @GetMapping("/me")
    public ResponseEntity<AdsDTO> getAdsMe(Authentication authentication) throws Exception {
        UserModel userModel = userService.findUserByUserName(authentication.getName());
        List<AdModel> ads = userModel.getAds();
        AdsDTO adsDTO = adMapper.mapListAdModelToAdsDTO(ads);
        return new ResponseEntity<>(adsDTO, HttpStatus.OK);
    }

    @Operation(
            summary = "Обновление картинки объявления",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = {
                                    @Content(
                                            mediaType = "application/octet-stream",
                                            array = @ArraySchema(
                                                    schema = @Schema(implementation = String.class)
                                            )
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            }, tags = "Объявления"
    )
    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = {MediaType.IMAGE_JPEG_VALUE},
            value = "/{id}/image")
    public ResponseEntity<byte[]> updateImage(@PathVariable int id, @RequestPart("image") MultipartFile image,
                                              Authentication authentication) throws IOException {

        AdModel updatingAd = adService.findAdById(id);
        adService.setImageToAd(updatingAd, image);
        byte[] bytes = image.getBytes();
        return new ResponseEntity<>(bytes, HttpStatus.OK);
    }
}
