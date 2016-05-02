<?php if ( ! defined('BASEPATH')) exit('No direct script access allowed');
 
class pessoa_model extends CI_Model {
     
    public function cadastrar(){
         
        // Inserção dos dados
        // `id_pessoa`, `ds_nomecompleto`, `dt_datanascimento`, `id_status`
        $this->db->insert('pessoa', array(
             
            'ds_nomecompleto' => $this->input->post('ds_nomecompleto', TRUE),
            'dt_datanascimento' => $this->input->post('dt_datanascimento', TRUE),
            'ds_senha' => md5($this->input->post('ds_senha', TRUE)),
            'ds_login' => $this->input->post('ds_login', TRUE),
        	'id_status' => $this->input->post('id_status', TRUE)
             
        )); 
    }
}