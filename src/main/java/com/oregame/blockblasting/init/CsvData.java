package com.oregame.blockblasting.init;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvData {
  public static final List<Map<String, String>> RAWS;

  static {
    RAWS = loadCsv("/data/raws.csv");
  }

  private static List<Map<String, String>> loadCsv(String filePath) {
    List<Map<String, String>> data = new ArrayList<>();
    try (InputStream inputStream = CsvData.class.getResourceAsStream(filePath)) {
      if (inputStream == null) {
        System.err.println("Resource not found: " + filePath);
        return data;
      }
      
      try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        String[] headers = null;

        while ((line = br.readLine()) != null) {
          if (headers == null) {
            // First line is the header
            headers = line.split(",");
          } else {
            // Subsequent lines are data rows
            String[] values = line.split(",");
            Map<String, String> map = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
              if (i < values.length) {
                map.put(headers[i], values[i]);
              } else {
                map.put(headers[i], "");
              }
            }
            data.add(map);
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace(); // For debugging purposes, can be replaced with proper logging
    }
    return data;
  }
  
  public static String[] processTags(String tagString) {
    // 空の文字列が渡された場合、要素0の配列を返す
    if (tagString == null || tagString.isEmpty()) {
      return new String[0];
    }
    
    // セミコロンで分割
    String[] tags = tagString.split(";");
    List<String> processedTags = new ArrayList<>();
    
    // 各タグについて処理
    for (String tag : tags) {
      String[] parts = tag.split("/");
      StringBuilder currentTag = new StringBuilder();
      
      // 部分タグを生成してリストに追加
      for (int i = 0; i < parts.length; i++) {
        if (i > 0) {
          currentTag.append("/");
        }
        currentTag.append(parts[i]);
        processedTags.add(currentTag.toString());
      }
    }
    
    // リストを配列に変換して返す
    return processedTags.toArray(new String[0]);
  }
}
