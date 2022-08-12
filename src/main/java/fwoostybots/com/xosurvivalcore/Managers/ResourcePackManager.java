package fwoostybots.com.xosurvivalcore.Managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ResourcePackManager {

    private final Map<UUID, Boolean> resourcepackStatusMap = new HashMap<>();

    public boolean getStatus(UUID playerId) {
        return resourcepackStatusMap.get(playerId);
    }

    public void setStatus(UUID playerId, boolean status) {
        resourcepackStatusMap.put(playerId, status);
    }

    public void remove(UUID playerId) {
        resourcepackStatusMap.remove(playerId);
    }

}
