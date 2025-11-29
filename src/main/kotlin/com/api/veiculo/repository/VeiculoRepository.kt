package com.api.veiculo.repository

import com.api.veiculo.dto.VeiculosReportResponseDto
import com.api.veiculo.enums.VeiculoStatus
import com.api.veiculo.entity.Veiculo
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.math.BigDecimal

interface VeiculoRepository : JpaRepository<Veiculo, Long> {

    fun findByStatus(status: VeiculoStatus, pageable: Pageable): Page<Veiculo>

    @Query(
        """
        SELECT v FROM tbl_veiculo v
        WHERE (:marca IS NULL OR v.marca = :marca)
          AND (:ano IS NULL OR v.ano = :ano)
          AND (:cor IS NULL OR v.cor = :cor)
    """
    )
    fun findByDetalhes(
        @Param("marca") marca: String?,
        @Param("ano") ano: String?,
        @Param("cor") cor: String?,
        pageable: Pageable
    ): Page<Veiculo>

    @Query(
        """
        SELECT v FROM tbl_veiculo v
        WHERE (:valorMinimo IS NULL OR v.valorMinimo >= :valorMinimo)
          AND (:valorMaximo IS NULL OR v.valorMaximo <= :valorMaximo)
    """
    )
    fun findByFaixaDePreco(
        @Param("valorMinimo") valorMinimo: BigDecimal?,
        @Param("valorMaximo") valorMaximo: BigDecimal?,
        pageable: Pageable
    ): Page<Veiculo>

    @Query("""
        SELECT new com.api.veiculo.dto.VeiculosReportResponse(v.marca, COUNT(v))
        FROM tbl_veiculo v
        GROUP BY v.marca
        ORDER BY COUNT(v) DESC
    """)
    fun reportByMarca(): List<VeiculosReportResponseDto>

}