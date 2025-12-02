use aes::Aes128;
use block_modes::{BlockMode, Cbc, Ecb};
use block_modes::block_padding::Pkcs7;
use rand::Rng;
use std::time::SystemTime;

type Aes128Ecb = Ecb<Aes128, Pkcs7>;

// 常量定义与Kotlin版本保持一致
const AES_KEY_1: &str = "bN7DVjFMJPcoWDcQ"; // 银川集团
const AES_KEY_2: &str = "bN7EVjFMJPOmWDcB"; // 非银川集团
const TICKET_SEPARATOR: &str = "##";

/// AES加密工具类，实现与Kotlin版本AESUtils相同的功能
pub struct AESUtils;

impl AESUtils {
    /// 使用AES加密字节数组
    pub fn encrypt(src: &[u8], key: &str) -> Result<Vec<u8>, Box<dyn std::error::Error>> {
        // 确保密钥长度为16字节(AES-128)
        let key_bytes = key.as_bytes();
        if key_bytes.len() != 16 {
            return Err("AES key must be 16 bytes".into());
        }
        
        let cipher = Aes128Ecb::new_from_slices(key_bytes, &[])?;
        let ciphertext = cipher.encrypt_vec(src);
        Ok(ciphertext)
    }

    /// 使用AES加密字符串并返回十六进制表示
    pub fn encrypt_string(data: &str, key: &str) -> Result<String, Box<dyn std::error::Error>> {
        let encrypted = Self::encrypt(data.as_bytes(), key)?;
        Ok(byte2hex(&encrypted))
    }

    /// 生成令牌，与Kotlin版本getToken方法逻辑一致
    pub fn get_token(id: Option<u64>, name: Option<&str>, group: Option<u64>) -> Result<String, Box<dyn std::error::Error>> {
        let user_id = id.unwrap_or(67);
        let user_name = name.unwrap_or("超级管理员1");
        let group_id = group.unwrap_or(2);
        
        // 根据集团ID选择合适的密钥
        let aes_key = if group_id == 1 || group_id == 2 {
            AES_KEY_1
        } else {
            AES_KEY_2
        };
        
        // 生成随机数
        let mut rng = rand::thread_rng();
        let random_num: i32 = rng.gen();
        
        // 获取当前时间戳
        let timestamp = SystemTime::now()
            .duration_since(SystemTime::UNIX_EPOCH)?
            .as_millis() as u64;
        
        // 构建待加密信息字符串
        let info = format!(
            "{}{}{}{}{}{}{}{}",
            random_num, TICKET_SEPARATOR, user_id, TICKET_SEPARATOR,
            user_name, TICKET_SEPARATOR, group_id, TICKET_SEPARATOR, timestamp
        );
        
        // 加密并返回结果
        Self::encrypt_string(&info, aes_key)
    }
}

/// 将字节数组转换为十六进制字符串，与Kotlin版本byte2hex方法行为一致
fn byte2hex(b: &[u8]) -> String {
    let mut hs = String::new();
    for &value in b {
        let stmp = format!("{:02x}", value);
        hs.push_str(&stmp);
    }
    hs.to_uppercase()
}

#[cfg(test)]
mod tests {
    use super::*;
    
    #[test]
    fn test_encrypt_and_decrypt() {
        // 测试加密功能是否正常工作
        let data = "test_data";
        let key = AES_KEY_1;
        
        let encrypted = AESUtils::encrypt_string(data, key).unwrap();
        assert!(!encrypted.is_empty());
    }
    
    #[test]
    fn test_get_token() {
        // 测试令牌生成功能
        let token = AESUtils::get_token(Some(123), Some("test_user"), Some(2)).unwrap();
        assert!(!token.is_empty());
        assert!(token.len() > 32); // 确保生成了合理长度的令牌
    }
    
    #[test]
    fn test_byte2hex() {
        // 测试字节转十六进制功能
        let bytes = [0x01, 0x02, 0xab, 0xcd];
        let hex = byte2hex(&bytes);
        assert_eq!(hex, "0102ABCD");
    }
}