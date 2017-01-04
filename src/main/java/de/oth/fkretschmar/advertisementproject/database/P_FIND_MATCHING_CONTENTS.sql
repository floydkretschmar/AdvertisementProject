/* 
 * Copyright (C) 2016 fkre
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
/**
 * Creates the stored procedure use to select the best matching content.
 *
 * Author:  fkre
 * Created: 10.12.2016
 */
DELIMITER //
CREATE PROCEDURE `P_FIND_MATCHING_CONTENTS`(IN TARGET_AGE VARCHAR(255),
     IN TARGET_GENDER VARCHAR(255),
     IN TARGET_MARITAL_STATUS VARCHAR(255),
     IN TARGET_PURPOSE_OF_USE VARCHAR(255))
BEGIN
    SELECT      content.ID, 
                COUNT(CONTEXT_SOURCE) as MATCHED_GROUPS, 
                SUM(MATCHED_VALUES) as MATCHES_IN_GROUPS
    FROM    
            (SELECT     tc.ID as CONTEXT_ID, 
                        'AGE' as CONTEXT_SOURCE, 
                        COUNT(ta.AGE) as MATCHED_VALUES
            FROM 	T_TARGET_CONTEXT tc
            INNER JOIN 	T_TARGET_AGE ta ON ta.CONTEXT_ID = tc.ID
            WHERE 	INSTR(TARGET_AGE, ta.AGE) <> 0
            GROUP BY 	tc.ID, 'AGE'
                UNION ALL
            SELECT 	tc.ID as CONTEXT_ID, 
                        'GENDER' as CONTEXT_SOURCE, 
                        COUNT(tg.GENDER) as MATCHED_VALUES
            FROM 	T_TARGET_CONTEXT tc
            INNER JOIN 	T_TARGET_GENDER tg ON tg.CONTEXT_ID = tc.ID
            WHERE 	INSTR(TARGET_GENDER, tg.GENDER) <> 0
            GROUP BY 	tc.ID, 'GENDER'
                    UNION ALL
            SELECT      tc.ID as CONTEXT_ID, 
                        'MARITAL_STATUS' as CONTEXT_SOURCE, 
                        COUNT(tm.MARITAL_STATUS) as MATCHED_VALUES
            FROM 	T_TARGET_CONTEXT tc
            INNER JOIN 	T_TARGET_MARITAL_STATUS tm ON tm.CONTEXT_ID = tc.ID
            WHERE 	INSTR(TARGET_MARITAL_STATUS, tm.MARITAL_STATUS) <> 0
            GROUP BY 	tc.ID, 'MARITAL_STATUS'
                    UNION ALL
            SELECT 	tc.ID as CONTEXT_ID, 
                        'PURPOSE_OF_USE' as CONTEXT_SOURCE, 
                        COUNT(tp.PURPOSE_OF_USE) as MATCHED_VALUES
            FROM 	T_TARGET_CONTEXT tc
            INNER JOIN 	T_TARGET_PURPOSE_OF_USE tp ON tp.CONTEXT_ID = tc.ID
            WHERE 	INSTR(TARGET_PURPOSE_OF_USE, tp.PURPOSE_OF_USE) <> 0
            GROUP BY 	tc.ID, 'PURPOSE_OF_USE') AS INNER_TABLE
    INNER JOIN  T_CONTENT content ON content.CONTEXT_ID = INNER_TABLE.CONTEXT_ID
    INNER JOIN  T_CAMPAIGN campaign ON content.CAMPAIGN_ID = campaign.ID
    WHERE       content.NUMER_OF_REQUESTS > 0
    AND         campaign.CAMPAIGN_STATE = 'RUNNING' 
    GROUP BY 	content.ID
    ORDER BY 	MATCHED_GROUPS DESC, MATCHES_IN_GROUPS DESC;
END //
DELIMITER FIND_MATCHING_CONTENTS;
