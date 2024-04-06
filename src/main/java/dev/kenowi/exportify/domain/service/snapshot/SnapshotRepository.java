package dev.kenowi.exportify.domain.service.snapshot;

import dev.kenowi.exportify.domain.entities.Snapshot;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface SnapshotRepository extends CrudRepository<Snapshot, UUID> {

}