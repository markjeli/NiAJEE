package com.jelinski.niajee.motorcycle.controller.api;

import com.jelinski.niajee.motorcycle.dto.GetMotorcycleResponse;
import com.jelinski.niajee.motorcycle.dto.GetMotorcyclesResponse;
import com.jelinski.niajee.motorcycle.dto.function.PatchMotorcycleRequest;
import com.jelinski.niajee.motorcycle.dto.function.PutMotorcycleRequest;

import java.util.UUID;


/**
 * Controller for managing collections motorcycles' representations.
 */
public interface MotorcycleController { //TODO: Implement patch, put and portrait methods

    /**
     * @return all motorcycles representation
     */
    GetMotorcyclesResponse getMotorcycles();

    /**
     * @param id motorcycle's id
     * @return motorcycle representation
     */
    GetMotorcycleResponse getMotorcycle(UUID id);

//    /**
//     * @param request new motorcycle representation
//     */
//    void putMotorcycle(UUID id, PutMotorcycleRequest request);
//
//    /**
//     * @param id      motorcycle's id
//     * @param request motorcycle update representation
//     */
//    void patchMotorcycle(UUID id, PatchMotorcycleRequest request);

    /**
     * @param id motorcycle's id
     */
    void deleteMotorcycle(UUID id);

//    /**
//     * @param id motorcycle's id
//     * @return motorcycle's photo
//     */
//    byte[] getMotorcyclePhoto(UUID id);

}
